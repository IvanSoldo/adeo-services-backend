package com.adeo.services.service.service;

import com.adeo.services.entity.Service;
import com.adeo.services.entity.ServiceType;
import com.adeo.services.repository.ServiceRepository;
import com.adeo.services.repository.ServiceTypeRepository;
import com.adeo.services.service.serviceType.ServiceTypeNotFoundException;
import java.util.List;
import com.adeo.services.repository.ServiceSpecifications;

@org.springframework.stereotype.Service
public class ServiceServiceImpl implements ServiceService {

  private final ServiceRepository serviceRepository;
  private final ServiceTypeRepository serviceTypeRepository;

  public ServiceServiceImpl(ServiceRepository serviceRepository, ServiceTypeRepository serviceTypeRepository) {

    this.serviceRepository = serviceRepository;
    this.serviceTypeRepository = serviceTypeRepository;
  }

  @Override
  public List<Service> getAll() {
    return serviceRepository.findAll();
  }

  @Override
  public void update(Service service) {
    Service serviceToUpdate = serviceRepository.findByIdAndIsDeletedFalse(service.getId())
        .orElseThrow(() -> new ServiceNotFoundException("Service not found with id: " + service.getId()));
    serviceToUpdate.update(service);
    serviceRepository.save(serviceToUpdate);
  }

  @Override
  public int create(Service service,Integer serviceTypeId) {
    ServiceType serviceType = serviceTypeRepository.findByIdAndIsDeletedFalse(serviceTypeId)
        .orElseThrow(() -> new ServiceTypeNotFoundException("ServiceType not found with id: " + serviceTypeId));
    service.setServiceType(serviceType);
    serviceType.add(service);
    Service savedService = serviceRepository.save(service);
    serviceTypeRepository.save(serviceType);
    return savedService.getId();
  }

  @Override
  public void delete(int id) {
    Service service = serviceRepository.findByIdAndIsDeletedFalse(id)
        .orElseThrow(() -> new ServiceNotFoundException("Service not found with id: " + id));
    service.setDeleted(true);
    serviceRepository.save(service);
  }

  @Override
  public List<Service> getByServiceTypeId(Integer serviceTypeId) {
    boolean isServiceTypeExist = this.serviceTypeRepository.existsById(serviceTypeId);

    if (!isServiceTypeExist) {
      throw new ServiceTypeNotFoundException("ServiceType not found with id: " + serviceTypeId);
    }

    return this.serviceRepository.findAll(
        ServiceSpecifications.getByServiceTypeId(serviceTypeId)
            .and(ServiceSpecifications.getByEnabledTrue())
            .and(ServiceSpecifications.getByServiceTypeDeletedFalse())
            .and(ServiceSpecifications.getByDeletedFalse())
    );
  }
}