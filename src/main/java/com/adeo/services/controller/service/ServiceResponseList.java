package com.adeo.services.controller.service;

import com.adeo.services.entity.Service;
import java.util.List;
import java.util.stream.Collectors;

public class ServiceResponseList {

  private final List<ServiceResponse> services;

  private ServiceResponseList(List<ServiceResponse> services) {
    this.services = services;
  }

  public static ServiceResponseList from(List<Service> services){
    List<ServiceResponse> serviceResponses = services.stream().map(ServiceResponse::from).collect(Collectors.toList());
    return new ServiceResponseList(serviceResponses);
  }

  public List<ServiceResponse> getServices() {
    return services;
  }
}
