package com.adeo.services.controller.serviceType;

import com.adeo.services.entity.ServiceType;
import java.util.List;
import java.util.stream.Collectors;

public class ServiceTypeWithServiceResponseList {

  private final List<ServiceTypeWithAllLanguagesResponse> servicesTypes;

  private ServiceTypeWithServiceResponseList(List<ServiceTypeWithAllLanguagesResponse> servicesTypes) {
    this.servicesTypes = servicesTypes;
  }

  public static ServiceTypeWithServiceResponseList from(List<ServiceType> servicesTypes) {
    List<ServiceTypeWithAllLanguagesResponse> serviceTypeWithAllLanguagesRespons = servicesTypes.stream()
        .map(ServiceTypeWithAllLanguagesResponse::from)
        .collect(Collectors.toList());
    return new ServiceTypeWithServiceResponseList(serviceTypeWithAllLanguagesRespons);
  }

  public List<ServiceTypeWithAllLanguagesResponse> getServicesTypes() {
    return servicesTypes;
  }
}
