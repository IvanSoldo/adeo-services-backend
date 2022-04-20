package com.adeo.services.controller.order;

import com.adeo.services.controller.AdeoErrorMessage;
import com.adeo.services.entity.Order;
import com.adeo.services.service.firewall.IpAddress;
import com.adeo.services.service.firewall.InvalidIpAddressException;
import com.adeo.services.service.order.OrderNotFoundException;
import com.adeo.services.service.order.OrderNotValidException;
import com.adeo.services.service.order.OrderService;
import com.adeo.services.service.order.OrderStatus;
import com.adeo.services.service.room.RoomNotFoundException;
import com.adeo.services.service.service.ServiceNotFoundException;
import com.adeo.services.service.serviceType.ServiceTypeNotFoundException;
import java.security.Principal;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.List;
import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.format.annotation.DateTimeFormat.ISO;
import org.springframework.http.HttpStatus;
import org.springframework.security.access.annotation.Secured;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class OrderController {

  private final OrderService orderService;

  @Autowired
  public OrderController(OrderService orderService) {
    this.orderService = orderService;
  }

  @GetMapping("api/v1/orders/units")
  public AvailableUnitsResponse checkAvailableUnits(
      @RequestParam("startAt") @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME) LocalDateTime startAt,
      @RequestParam("endAt") @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME) LocalDateTime endAt,
      @RequestParam int serviceTypeId,
      Principal principal) {
    Order order = new Order();
    order.setStartAt(startAt);
    order.setEndAt(endAt);

    this.trimOrderMinutes(order);

    boolean isAuthenticatedUser = principal != null;

    int availableUnits = this.orderService.checkAvailableUnits(order, serviceTypeId, isAuthenticatedUser);

    return AvailableUnitsResponse.from(availableUnits);
  }

  @Secured({"ROLE_ADMIN", "ROLE_RECEPTIONIST"})
  @GetMapping("api/v1/orders")
  public OrderResponseList getOrders(Pageable pageable, @RequestParam OrderStatus orderStatus) {
    Page<Order> getOrdersPerPage = orderService.getAll(pageable, orderStatus);
    return OrderResponseList.from(getOrdersPerPage.getContent(), getOrdersPerPage.getTotalPages());
  }

  @Secured({"ROLE_ADMIN", "ROLE_RECEPTIONIST"})
  @GetMapping("api/v1/orders/{serviceTypeId}")
  public OrderChartResponseList getOrdersByDate(@RequestParam("date") @DateTimeFormat(iso = ISO.DATE) LocalDate date, @PathVariable("serviceTypeId") int serviceTypeId) {
    List<Order> orders = this.orderService.getAllByDate(date, serviceTypeId);
    return OrderChartResponseList.from(orders);
  }

  @PostMapping("api/v1/orders")
  @ResponseStatus(HttpStatus.OK)
  public OrderResponse create(@RequestBody @Valid OrderCreateRequest orderCreateRequest, Principal principal, HttpServletRequest request) {
    Order order = orderCreateRequest.from();
    this.trimOrderMinutes(order);

    boolean isAuthenticatedUser = principal != null;
    IpAddress ipAddress = new IpAddress(request.getRemoteAddr());

    order = this.orderService.create(order, orderCreateRequest.getServiceTypeId(), orderCreateRequest.getRoomId(), isAuthenticatedUser, ipAddress);

    return OrderResponse.from(order);
  }

  @Secured({"ROLE_ADMIN", "ROLE_RECEPTIONIST"})
  @DeleteMapping("api/v1/orders/{id}")
  @ResponseStatus(HttpStatus.NO_CONTENT)
  public void delete(@PathVariable("id") int id) {
    this.orderService.delete(id);
  }

  @Secured({"ROLE_ADMIN", "ROLE_RECEPTIONIST"})
  @PatchMapping("api/v1/orders/{id}")
  @ResponseStatus(HttpStatus.NO_CONTENT)
  public void update(@PathVariable int id, @RequestBody @Valid OrderUpdateRequest orderUpdateRequest) {
    Order order = orderUpdateRequest.from(id);
    this.orderService.update(order, orderUpdateRequest.getServiceToRemove(), orderUpdateRequest.getServiceToAdd(), orderUpdateRequest.getServiceTypeId());
  }

  private void trimOrderMinutes(Order order) {
    order.setStartAt(order.getStartAt().withMinute(0));
    LocalTime maxEndAt = LocalTime.of(23, 59);
    if (!order.getEndAt().toLocalTime().equals(maxEndAt)) {
      order.setEndAt(order.getEndAt().withMinute(0));
    }
  }

  @ExceptionHandler(OrderNotValidException.class)
  @ResponseStatus(HttpStatus.BAD_REQUEST)
  AdeoErrorMessage handleOrderNotValid(OrderNotValidException e) {
    return new AdeoErrorMessage(e.getMessage());
  }

  @ExceptionHandler(RoomNotFoundException.class)
  @ResponseStatus(HttpStatus.BAD_REQUEST)
  AdeoErrorMessage handleRoomNotFound(RoomNotFoundException e) {
    return new AdeoErrorMessage(e.getMessage());
  }

  @ExceptionHandler(OrderNotFoundException.class)
  @ResponseStatus(HttpStatus.BAD_REQUEST)
  AdeoErrorMessage handleOrderNotFound(OrderNotFoundException e) {
    return new AdeoErrorMessage(e.getMessage());
  }

  @ExceptionHandler(ServiceTypeNotFoundException.class)
  @ResponseStatus(HttpStatus.BAD_REQUEST)
  AdeoErrorMessage handleServiceTypeNotFound(ServiceTypeNotFoundException e) {
    return new AdeoErrorMessage(e.getMessage());
  }

  @ExceptionHandler(InvalidIpAddressException.class)
  @ResponseStatus(HttpStatus.FORBIDDEN)
  AdeoErrorMessage handleInvalidIpAddressException(InvalidIpAddressException e) {
    return new AdeoErrorMessage(e.getMessage());
  }

  @ExceptionHandler(ServiceNotFoundException.class)
  @ResponseStatus(HttpStatus.BAD_REQUEST)
  AdeoErrorMessage handleServiceNotFound(ServiceNotFoundException e) {
    return new AdeoErrorMessage(e.getMessage());
  }

}