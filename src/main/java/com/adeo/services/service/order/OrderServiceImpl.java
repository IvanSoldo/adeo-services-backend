package com.adeo.services.service.order;

import com.adeo.services.entity.Order;
import com.adeo.services.entity.Room;
import com.adeo.services.entity.Service;
import com.adeo.services.entity.ServiceType;
import com.adeo.services.repository.OrderRepository;
import com.adeo.services.repository.RoomRepository;
import com.adeo.services.repository.ServiceRepository;
import com.adeo.services.repository.ServiceSpecifications;
import com.adeo.services.repository.ServiceTypeRepository;
import com.adeo.services.service.firewall.FirewallService;
import com.adeo.services.service.firewall.InvalidIpAddressException;
import com.adeo.services.service.firewall.IpAddress;
import com.adeo.services.service.room.RoomNotFoundException;
import com.adeo.services.service.service.ServiceNotFoundException;
import com.adeo.services.service.serviceType.ServiceTypeNotFoundException;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.List;
import javax.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;

@org.springframework.stereotype.Service
public class OrderServiceImpl implements OrderService {

  private final OrderRepository orderRepository;

  private final ServiceRepository serviceRepository;

  private final RoomRepository roomRepository;

  private final ServiceTypeRepository serviceTypeRepository;

  private final OrderValidatorImpl orderValidatorImpl;

  private final FirewallService firewallService;

  @Autowired
  public OrderServiceImpl(OrderRepository orderRepository,
      ServiceRepository serviceRepository,
      RoomRepository roomRepository,
      ServiceTypeRepository serviceTypeRepository,
      OrderValidatorImpl orderValidatorImpl,
      FirewallService firewallService) {
    this.orderRepository = orderRepository;
    this.serviceRepository = serviceRepository;
    this.roomRepository = roomRepository;
    this.serviceTypeRepository = serviceTypeRepository;
    this.orderValidatorImpl = orderValidatorImpl;
    this.firewallService = firewallService;
  }

  @Override
  public Order create(Order order, Integer serviceTypeId, String roomId, boolean isAuthenticatedUser, IpAddress ipAddress) {

    if(!isAuthenticatedUser){
      boolean hasFirewallPermission = firewallService.hasPermission(ipAddress);

      if(!hasFirewallPermission){
        throw new InvalidIpAddressException("Invalid ip address");
      }
    }

    int availableUnitsCount = this.checkAvailableUnits(order, serviceTypeId, isAuthenticatedUser);

    if (order.getAmount() > availableUnitsCount || order.getAmount() < 1 || order.getAmount() > 4) {
      throw new OrderNotValidException("Something went wrong. Please try again or contact our staff.");
    }

    Room room = this.roomRepository.findByUuidAndIsDeletedFalse(roomId)
        .orElseThrow(() -> new RoomNotFoundException("Room not found with id: " + roomId));

    List<Service> availableServices = this.serviceRepository.findAll(
        ServiceSpecifications.getByServiceTypeId(serviceTypeId)
            .and(ServiceSpecifications.getByEnabledTrue())
            .and(ServiceSpecifications.getByServiceTypeDeletedFalse())
            .and(ServiceSpecifications.isNotBooked(order.getStartAt(), order.getEndAt()))
            .and(ServiceSpecifications.getByDeletedFalse())
    );

    List<Service> servicesToBook = availableServices.subList(0, order.getAmount());

    order.setRoom(room);
    order.setServices(servicesToBook);
    order.setCreatedAt(LocalDateTime.now(ZoneId.of("CET")));

    this.orderRepository.save(order);

    return order;
  }

  @Override
  @Transactional
  public int checkAvailableUnits(Order order, Integer serviceTypeId, boolean isAuthenticatedUser) {
    this.checkIfOrderIsValid(order, isAuthenticatedUser);

    ServiceType serviceTypeFromDb = this.serviceTypeRepository.findByIdAndIsDeletedFalse(serviceTypeId)
        .orElseThrow(() -> new ServiceTypeNotFoundException("ServiceType with id: " + serviceTypeId + " not found"));

    long availableServices = this.serviceRepository.count(
        ServiceSpecifications.getByServiceTypeId(serviceTypeId)
            .and(ServiceSpecifications.getByEnabledTrue())
            .and(ServiceSpecifications.getByServiceTypeDeletedFalse())
            .and(ServiceSpecifications.isNotBooked(order.getStartAt(), order.getEndAt()))
            .and(ServiceSpecifications.getByDeletedFalse())
    );

    return (int) availableServices;
  }

  @Override
  public void delete(int id) {
    boolean isOrderExist = this.orderRepository.existsById(id);
    if (!isOrderExist) {
      throw new OrderNotFoundException("Order not found with id: " + id);
    }
    this.orderRepository.deleteById(id);
  }

  @Override
  public Page<Order> getAll(Pageable pageable, OrderStatus orderStatus) {

    Pageable sortedByCreatedAt = PageRequest
        .of(pageable.getPageNumber(), pageable.getPageSize(), Sort.by("createdAt").descending());

    LocalDateTime currentDateTime = LocalDateTime.now(ZoneId.of("CET"));

    if (orderStatus == OrderStatus.OCCURRING) {
      return orderRepository.findByStartAtLessThanEqualAndEndAtGreaterThanEqual(currentDateTime, currentDateTime, sortedByCreatedAt);
    } else if (orderStatus == OrderStatus.COMPLETED) {
      return orderRepository.findByEndAtLessThanEqual(currentDateTime, sortedByCreatedAt);
    } else if (orderStatus == OrderStatus.PENDING) {
      return orderRepository.findByStartAtGreaterThanEqual(currentDateTime, sortedByCreatedAt);
    } else {
      return orderRepository.findAll(sortedByCreatedAt);
    }
  }

  @Override
  public List<Order> getAllByDate(LocalDate date, int serviceTypeId) {
    boolean isServiceTypeExist = this.serviceTypeRepository.existsById(serviceTypeId);

    if (!isServiceTypeExist) {
      throw new ServiceTypeNotFoundException("ServiceType with id: " + serviceTypeId + " not found");
    }

    return this.orderRepository.findDistinctByEndAtBetweenAndServicesServiceTypeIdAndServicesIsDeletedFalseAndServicesIsEnabledTrue(date.atStartOfDay(), date.plusDays(1).atStartOfDay(), serviceTypeId);
  }

  @Override
  public void update(Order order, String serviceToRemove, String serviceToAdd, int serviceTypeId) {
    Order orderFromDb = this.orderRepository.findById(order.getId())
        .orElseThrow(() -> new OrderNotFoundException("Order with id: " + order.getId() + " not found"));

    Service serviceFromDbToRemove = this.serviceRepository.findOne(
        ServiceSpecifications.getByEnabledTrue()
            .and(ServiceSpecifications.getByServiceTypeId(serviceTypeId))
            .and(ServiceSpecifications.getByDeletedFalse())
            .and(ServiceSpecifications.getByName(serviceToRemove))
    ).orElseThrow(() -> new ServiceNotFoundException(
        "Service with name: " + serviceToRemove + " not found"));

    boolean isServiceInOrder = order.getServices().contains(serviceFromDbToRemove);

    if (isServiceInOrder) {
      throw new ServiceNotFoundException(
          "Service with name: " + serviceToRemove + " not found in the requested order");
    }

    Service serviceFromDbToAdd = this.serviceRepository.findOne(
        ServiceSpecifications.getByEnabledTrue()
            .and(ServiceSpecifications.getByServiceTypeId(serviceTypeId))
            .and(ServiceSpecifications.getByDeletedFalse())
            .and(ServiceSpecifications.getByName(serviceToAdd))
            .and(ServiceSpecifications.isNotBooked(orderFromDb.getStartAt(), orderFromDb.getEndAt()))
    ).orElseThrow(() -> new ServiceNotFoundException("Service with name: " + serviceToAdd + " not available"));

    orderFromDb.update(serviceFromDbToRemove, serviceFromDbToAdd);
    this.orderRepository.save(orderFromDb);
  }

  private void checkIfOrderIsValid(Order order, boolean isAdmin) {

    OrderValidator orderValidator = null;

    if(isAdmin){
      orderValidator = new AdminOrderValidator();
    } else {
      orderValidator = new GuestOrderValidator();
    }

    boolean isOrderValid = orderValidator.isValid(order);

    if (!isOrderValid) {
      throw new OrderNotValidException("Something went wrong. Please try again or contact our staff.");
    }
  }

}