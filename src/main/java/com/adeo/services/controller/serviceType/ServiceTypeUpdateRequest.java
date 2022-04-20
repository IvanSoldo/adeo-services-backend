package com.adeo.services.controller.serviceType;

import com.adeo.services.entity.ServiceType;
import javax.validation.constraints.NotBlank;

public class ServiceTypeUpdateRequest {

  @NotBlank
  private String nameHr;
  @NotBlank
  private String nameDe;
  @NotBlank
  private String nameIt;
  @NotBlank
  private String nameEn;

  public ServiceType from(Integer id){
    ServiceType serviceType = new ServiceType();
    serviceType.setId(id);
    serviceType.setNameHr(nameHr);
    serviceType.setNameDe(nameDe);
    serviceType.setNameIt(nameIt);
    serviceType.setNameEn(nameEn);
    return serviceType;
  }

  public String getNameHr() {
    return nameHr;
  }

  public void setNameHr(String nameHr) {
    this.nameHr = nameHr;
  }

  public String getNameDe() {
    return nameDe;
  }

  public void setNameDe(String nameDe) {
    this.nameDe = nameDe;
  }

  public String getNameIt() {
    return nameIt;
  }

  public void setNameIt(String nameIt) {
    this.nameIt = nameIt;
  }

  public String getNameEn() {
    return nameEn;
  }

  public void setNameEn(String nameEn) {
    this.nameEn = nameEn;
  }
}
