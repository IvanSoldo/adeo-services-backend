package com.adeo.services.controller.order;

import com.adeo.services.entity.Order;
import javax.validation.constraints.NotNull;

public class OrderUpdateRequest {

  @NotNull
  private String serviceToRemove;

  @NotNull
  private String serviceToAdd;

  @NotNull
  private Integer serviceTypeId;

  public Order from(int id) {
    Order order = new Order();
    order.setId(id);
    return order;
  }

  public String getServiceToRemove() {
    return serviceToRemove;
  }

  public void setServiceToRemove(String serviceToRemove) {
    this.serviceToRemove = serviceToRemove;
  }

  public String getServiceToAdd() {
    return serviceToAdd;
  }

  public void setServiceToAdd(String serviceToAdd) {
    this.serviceToAdd = serviceToAdd;
  }

  public Integer getServiceTypeId() {
    return serviceTypeId;
  }

  public void setServiceTypeId(Integer serviceTypeId) {
    this.serviceTypeId = serviceTypeId;
  }
}
