package com.adeo.services.controller.service;

import com.adeo.services.entity.Service;
import javax.validation.constraints.NotNull;

public class ServiceCreateRequest {

  @NotNull
  private String name;
  @NotNull
  private Boolean isEnabled;
  @NotNull
  private Integer serviceTypeId;

  public ServiceCreateRequest() {
  }

  public Service from() {
    return new Service(this.name, this.isEnabled);
  }

  public void setName(String name) {
    this.name = name;
  }

  public void setIsEnabled(boolean isEnabled) {
    this.isEnabled = isEnabled;
  }

  public String getName() {
    return name;
  }

  public Boolean getIsEnabled() {
    return isEnabled;
  }

  public Integer getServiceTypeId() {
    return serviceTypeId;
  }

  public void setServiceTypeId(Integer serviceTypeId) {
    this.serviceTypeId = serviceTypeId;
  }

}
