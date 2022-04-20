package com.adeo.services.controller.serviceType;

import com.adeo.services.controller.service.ServiceResponse;
import com.adeo.services.entity.ServiceType;
import java.util.List;
import java.util.stream.Collectors;

public class ServiceTypeWithAllLanguagesResponse {

  private final Integer id;
  private final String nameHr;
  private final String nameDe;
  private final String nameIt;
  private final String nameEn;
  private final List<ServiceResponse> services;

  public ServiceTypeWithAllLanguagesResponse(Integer id, String nameHr, String nameDe, String nameIt, String nameEn,
      List<ServiceResponse> services) {
    this.id = id;
    this.nameHr = nameHr;
    this.nameDe = nameDe;
    this.nameIt = nameIt;
    this.nameEn = nameEn;
    this.services = services;
  }

  public static ServiceTypeWithAllLanguagesResponse from(ServiceType serviceType) {
    List<ServiceResponse> services = serviceType.getServices().stream()
        .map(ServiceResponse::from)
        .collect(Collectors.toList());
    return new ServiceTypeWithAllLanguagesResponse(serviceType.getId(), serviceType.getNameHr(), serviceType.getNameDe(),
        serviceType.getNameIt(), serviceType.getNameEn(), services);
  }

  public Integer getId() {
    return id;
  }

  public String getNameHr() {
    return nameHr;
  }

  public String getNameDe() {
    return nameDe;
  }

  public String getNameIt() {
    return nameIt;
  }

  public String getNameEn() {
    return nameEn;
  }

  public List<ServiceResponse> getServices() {
    return services;
  }
}
