package com.adeo.services.service.order;

import com.adeo.services.entity.Order;

public interface OrderValidator {

  boolean isValid(Order order);

}
