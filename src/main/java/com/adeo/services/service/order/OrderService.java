package com.adeo.services.service.order;

import com.adeo.services.entity.Order;
import com.adeo.services.service.firewall.IpAddress;
import java.time.LocalDate;
import java.util.List;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface OrderService {

  Order create(Order order, Integer serviceTypeId, String roomId, boolean isAuthenticatedUser, IpAddress ipAddress);

  int checkAvailableUnits(Order order, Integer serviceTypeId, boolean isAuthenticatedUser);

  void delete(int id);

  Page<Order> getAll(Pageable pageable, OrderStatus orderStatus);

  List<Order> getAllByDate(LocalDate date, int serviceTypeId);

  void update(Order order, String serviceToRemove, String serviceToAdd, int serviceTypeId);

}