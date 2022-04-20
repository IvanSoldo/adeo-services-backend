package com.adeo.services.controller.service;

import com.adeo.services.entity.Service;
import javax.validation.constraints.NotNull;

public class ServiceUpdateRequest {

  @NotNull
  private String name;
  @NotNull
  private Boolean isEnabled;

  public ServiceUpdateRequest() {
  }

  public Service from(int id) {
    return new Service(id, this.name, this.isEnabled);
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

}
