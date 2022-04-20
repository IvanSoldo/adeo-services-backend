package com.adeo.services.service.order;

import com.adeo.services.entity.Order;

public class AdminOrderValidator implements OrderValidator {

  @Override
  public boolean isValid(Order order) {
    return !isStartAtAfterEndAt(order);
  }

  private boolean isStartAtAfterEndAt(Order order) {
    return order.getStartAt().isAfter(order.getEndAt());
  }
}
