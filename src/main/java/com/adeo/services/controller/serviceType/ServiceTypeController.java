package com.adeo.services.controller.serviceType;

import com.adeo.services.controller.AdeoErrorMessage;
import com.adeo.services.entity.ServiceType;
import com.adeo.services.service.serviceType.ServiceTypeNotFoundException;
import com.adeo.services.service.serviceType.ServiceTypeService;
import java.util.List;
import javax.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.security.access.annotation.Secured;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class ServiceTypeController {

  private final ServiceTypeService serviceTypeService;

  public ServiceTypeController(ServiceTypeService serviceTypeService) {
    this.serviceTypeService = serviceTypeService;
  }

  @Secured("ROLE_ADMIN")
  @GetMapping("api/v1/services-type/services")
  public ServiceTypeWithServiceResponseList getAllServicesWithServiceType() {
    List<ServiceType> servicesTypes = serviceTypeService.getAll();
    return ServiceTypeWithServiceResponseList.from(servicesTypes);
  }

  @GetMapping("api/v1/services-type")
  public ServiceTypeResponseList getAllServices(@RequestHeader(name = "accept-language", required = false) LanguageRequest language) {
    List<ServiceType> servicesTypes = serviceTypeService.getAll();
    return ServiceTypeResponseList.from(servicesTypes, language);
  }

  @Secured("ROLE_ADMIN")
  @ResponseStatus(HttpStatus.NO_CONTENT)
  @PatchMapping("api/v1/services-type/{id}")
  public void update(@PathVariable("id") int id, @RequestBody @Valid ServiceTypeUpdateRequest serviceTypeUpdateRequest) {
    ServiceType serviceType = serviceTypeUpdateRequest.from(id);
    serviceTypeService.update(serviceType);
  }

  @Secured("ROLE_ADMIN")
  @ResponseStatus(HttpStatus.OK)
  @PostMapping("api/v1/services-type")
  public ServiceTypeCreateResponse create(@RequestBody @Valid ServiceTypeCreateRequest servicetypeCreateRequest) {
    ServiceType serviceType = servicetypeCreateRequest.from();
    int serviceTypeId = serviceTypeService.create(serviceType);
    return new ServiceTypeCreateResponse(serviceTypeId);
  }

  @Secured("ROLE_ADMIN")
  @ResponseStatus(HttpStatus.NO_CONTENT)
  @DeleteMapping("api/v1/services-type/{id}")
  public void delete(@PathVariable("id") int id) {
    serviceTypeService.delete(id);
  }

  @ExceptionHandler(ServiceTypeNotFoundException.class)
  @ResponseStatus(HttpStatus.NOT_FOUND)
  AdeoErrorMessage handleServiceTypeNotFound(ServiceTypeNotFoundException e) {
    return new AdeoErrorMessage(e.getMessage());
  }
}
