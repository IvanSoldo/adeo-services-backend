package com.adeo.services.controller.serviceType;

import com.adeo.services.entity.ServiceType;

public class ServiceTypeResponse {

  private final Integer id;
  private final String name;

  public ServiceTypeResponse(Integer id, String name) {
    this.id = id;
    this.name = name;
  }

  public static ServiceTypeResponse from(ServiceType serviceType, LanguageRequest language) {
    return new ServiceTypeResponse(serviceType.getId(), getServiceTypeNameByLanguage(language, serviceType));
  }

  private static String getServiceTypeNameByLanguage(LanguageRequest language, ServiceType serviceType) {

    if (language == null) {
      return serviceType.getNameEn();
    } else if (language == LanguageRequest.DE) {
      return serviceType.getNameDe();
    } else if (language == LanguageRequest.EN) {
      return serviceType.getNameEn();
    } else if (language == LanguageRequest.HR) {
      return serviceType.getNameHr();
    } else if (language == LanguageRequest.IT) {
      return serviceType.getNameIt();
    } else {
      throw new IllegalArgumentException("Non existing Language");
    }

  }

  public Integer getId() {
    return id;
  }

  public String getName() {
    return name;
  }
}
