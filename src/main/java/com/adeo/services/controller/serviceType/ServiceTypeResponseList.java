package com.adeo.services.controller.serviceType;

import com.adeo.services.entity.ServiceType;
import java.util.List;
import java.util.stream.Collectors;

public class ServiceTypeResponseList {

  private final List<ServiceTypeResponse> servicesTypes;

  private ServiceTypeResponseList(List<ServiceTypeResponse> servicesTypes) {
    this.servicesTypes = servicesTypes;
  }

  public static ServiceTypeResponseList from(List<ServiceType> servicesTypes, LanguageRequest language) {
    List<ServiceTypeResponse> serviceTypeWithAllLanguagesRespons = servicesTypes.stream()
        .map(serviceType -> ServiceTypeResponse.from(serviceType, language))
        .collect(Collectors.toList());
    return new ServiceTypeResponseList(serviceTypeWithAllLanguagesRespons);
  }

  public List<ServiceTypeResponse> getServicesTypes() {
    return servicesTypes;
  }
}
