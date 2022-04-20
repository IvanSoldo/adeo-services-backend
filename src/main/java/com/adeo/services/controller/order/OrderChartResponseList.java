package com.adeo.services.controller.order;

import com.adeo.services.entity.Order;
import java.util.List;
import java.util.stream.Collectors;

public class OrderChartResponseList {

  private final List<OrderResponse> orders;


  private OrderChartResponseList(List<OrderResponse> orders) {
    this.orders = orders;
  }

  public static OrderChartResponseList from(List<Order> orders){
    List<OrderResponse> orderResponse = orders.stream().map(OrderResponse::from).collect(Collectors.toList());
    return new OrderChartResponseList(orderResponse);
  }

  public List<OrderResponse> getOrders() {
    return orders;
  }

}
