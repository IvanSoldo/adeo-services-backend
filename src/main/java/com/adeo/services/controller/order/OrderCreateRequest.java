package com.adeo.services.controller.order;

import com.adeo.services.entity.Order;

import javax.validation.constraints.NotNull;
import java.time.LocalDateTime;

public class OrderCreateRequest {

  @NotNull
  private LocalDateTime startAt;
  @NotNull
  private LocalDateTime endAt;
  @NotNull
  private Integer amount;
  @NotNull
  private Integer serviceTypeId;
  @NotNull
  private String roomId;

  public OrderCreateRequest() {
  }

  public Order from() {
    Order order = new Order();

    order.setStartAt(this.startAt);
    order.setEndAt(this.endAt);
    order.setAmount(this.amount);

    return order;
  }

  public LocalDateTime getStartAt() {
    return startAt;
  }

  public void setStartAt(LocalDateTime startAt) {
    this.startAt = startAt;
  }

  public LocalDateTime getEndAt() {
    return endAt;
  }

  public void setEndAt(LocalDateTime endAt) {
    this.endAt = endAt;
  }

  public Integer getAmount() {
    return amount;
  }

  public void setAmount(Integer amount) {
    this.amount = amount;
  }

  public Integer getServiceTypeId() {
    return serviceTypeId;
  }

  public void setServiceTypeId(Integer serviceTypeId) {
    this.serviceTypeId = serviceTypeId;
  }

  public String getRoomId() {
    return roomId;
  }

  public void setRoomId(String roomId) {
    this.roomId = roomId;
  }
}
