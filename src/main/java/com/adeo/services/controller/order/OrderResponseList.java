package com.adeo.services.controller.order;


    import com.adeo.services.entity.Order;
    import java.util.List;
    import java.util.stream.Collectors;

public class OrderResponseList {

  private final Integer totalPages;
  private final List<OrderResponse> orders;


  private OrderResponseList(List<OrderResponse> orders, Integer totalPages) {
    this.orders = orders;
    this.totalPages = totalPages;
  }

  public static OrderResponseList from(List<Order> orders, Integer totalPages){
    List<OrderResponse> orderResponse = orders.stream().map(OrderResponse::from).collect(Collectors.toList());
    return new OrderResponseList(orderResponse, totalPages);
  }

  public List<OrderResponse> getOrders() {
    return orders;
  }

  public Integer getTotalPages() {
    return totalPages;
  }
}
