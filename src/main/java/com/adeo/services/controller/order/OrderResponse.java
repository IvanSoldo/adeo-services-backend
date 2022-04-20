package com.adeo.services.controller.order;

import com.adeo.services.entity.Order;
import com.adeo.services.entity.Service;
import java.util.ArrayList;
import java.util.List;

public class OrderResponse {

  private Integer id;
  private String  roomNumber;
  private String  createdAt;
  private String   startAt;
  private String  endAt;
  private String  serviceType;
  private List<String> services;

  public OrderResponse(Integer id, String roomNumber, String createdAt, String startAt, String endAt,
      String serviceType, List<Service> services) {
    this.id = id;
    this.roomNumber = roomNumber;
    this.createdAt = createdAt;
    this.startAt = startAt;
    this.endAt = endAt;
    this.serviceType = serviceType;
    this.services = getServicesNames(services);
  }

  public OrderResponse() {
  }

  public static OrderResponse from(Order order){
    return new OrderResponse(order.getId(), order.getRoom().getNumber(), order.getCreatedAt().toString() ,order.getStartAt().toString() , order.getEndAt().toString(), order.getServices().get(0).getServiceType().getNameEn() ,order.getServices() );
  }

  private List<String> getServicesNames(List<Service> orderServices){
    List<String> servicesNames = new ArrayList<>();

    for (Service orderService : orderServices) {
      servicesNames.add(orderService.getName());
    }
    return servicesNames;
  }

  public Integer getId() {
    return id;
  }

  public void setId(Integer id) {
    this.id = id;
  }

  public String getRoomNumber() {
    return roomNumber;
  }

  public void setRoomNumber(String roomNumber) {
    this.roomNumber = roomNumber;
  }

  public String getCreatedAt() {
    return createdAt;
  }

  public void setCreatedAt(String createdAt) {
    this.createdAt = createdAt;
  }

  public String getStartAt() {
    return startAt;
  }

  public void setStartAt(String startAt) {
    this.startAt = startAt;
  }

  public String getEndAt() {
    return endAt;
  }

  public void setEndAt(String endAt) {
    this.endAt = endAt;
  }

  public String getServiceType() {
    return serviceType;
  }

  public void setServiceType(String serviceType) {
    this.serviceType = serviceType;
  }

  public List<String> getServices() {
    return services;
  }

  public void setServices(List<String> services) {
    this.services = services;
  }


}
