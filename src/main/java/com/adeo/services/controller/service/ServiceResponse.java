package com.adeo.services.controller.service;

import com.adeo.services.entity.Service;

public class ServiceResponse {

  private final Integer id;
  private final String name;
  private final Boolean isEnabled;
  private final Integer serviceTypeId;

  private ServiceResponse(Integer id, String name, Boolean isEnabled, Integer serviceTypeId) {
    this.id = id;
    this.name = name;
    this.isEnabled = isEnabled;
    this.serviceTypeId = serviceTypeId;
  }

  public static ServiceResponse from(Service service){
    return new ServiceResponse(service.getId(), service.getName(), service.isEnabled(),service.getServiceType().getId());
  }

  public int getId() {
    return id;
  }

  public String getName() {
    return name;
  }

  public boolean getIsEnabled() {
    return isEnabled;
  }

  public Integer getServiceTypeId() {
    return serviceTypeId;
  }
}
