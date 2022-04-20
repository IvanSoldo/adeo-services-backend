package com.adeo.services.controller.service;

import com.adeo.services.controller.AdeoErrorMessage;
import com.adeo.services.entity.Service;
import com.adeo.services.service.service.ServiceNotFoundException;
import com.adeo.services.service.service.ServiceService;
import com.adeo.services.service.serviceType.ServiceTypeNotFoundException;
import java.time.LocalDate;
import java.util.List;
import javax.validation.Valid;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.format.annotation.DateTimeFormat.ISO;
import org.springframework.http.HttpStatus;
import org.springframework.security.access.annotation.Secured;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class ServiceController {

  private final ServiceService serviceService;

  public ServiceController(ServiceService serviceService) {
    this.serviceService = serviceService;
  }

  @Secured("ROLE_ADMIN")
  @GetMapping("api/v1/services")
  public ServiceResponseList getAllServices() {
    List<Service> services = serviceService.getAll();
    return ServiceResponseList.from(services);
  }

  @Secured("ROLE_ADMIN")
  @ResponseStatus(HttpStatus.NO_CONTENT)
  @PatchMapping("api/v1/services/{id}")
  public void update(@PathVariable("id") int id, @RequestBody @Valid ServiceUpdateRequest serviceUpdateRequest) {
    Service service = serviceUpdateRequest.from(id);
    serviceService.update(service);
  }

  @Secured("ROLE_ADMIN")
  @ResponseStatus(HttpStatus.OK)
  @PostMapping("api/v1/services")
  public ServiceCreateResponse create(@RequestBody @Valid ServiceCreateRequest serviceCreateRequest) {
    Service service = serviceCreateRequest.from();
    int serviceId = serviceService.create(service, serviceCreateRequest.getServiceTypeId());
    return new ServiceCreateResponse(serviceId);
  }

  @Secured({"ROLE_ADMIN", "ROLE_RECEPTIONIST"})
  @GetMapping("api/v1/services/{serviceTypeId}")
  public ServiceResponseList getServicesByServiceTypeId(@PathVariable("serviceTypeId") int serviceTypeId) {
    List<Service> services = serviceService.getByServiceTypeId(serviceTypeId);
    return ServiceResponseList.from(services);
  }

  @Secured("ROLE_ADMIN")
  @ResponseStatus(HttpStatus.NO_CONTENT)
  @DeleteMapping("api/v1/services/{id}")
  public void delete(@PathVariable("id") int id) {
    serviceService.delete(id);
  }

  @ExceptionHandler(ServiceNotFoundException.class)
  @ResponseStatus(HttpStatus.NOT_FOUND)
  AdeoErrorMessage handleServiceNotFound(ServiceNotFoundException e) {
    return new AdeoErrorMessage(e.getMessage());
  }

  @ExceptionHandler(ServiceTypeNotFoundException.class)
  @ResponseStatus(HttpStatus.NOT_FOUND)
  AdeoErrorMessage handleServiceTypeNotFound(ServiceTypeNotFoundException e) {
    return new AdeoErrorMessage(e.getMessage());
  }

}
