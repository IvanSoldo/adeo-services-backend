package com.adeo.services.service.serviceType;

import com.adeo.services.entity.ServiceType;
import com.adeo.services.repository.ServiceTypeRepository;
import java.util.List;
import org.springframework.stereotype.Service;

@Service
public class ServiceTypeServiceImpl implements ServiceTypeService {

  private final ServiceTypeRepository serviceTypeRepository;

  public ServiceTypeServiceImpl(ServiceTypeRepository serviceTypeRepository) {
    this.serviceTypeRepository = serviceTypeRepository;
  }

  @Override
  public List<ServiceType> getAll() {
    return serviceTypeRepository.findByIsDeletedFalse();
  }

  @Override
  public void update(ServiceType serviceType) {
    ServiceType serviceTypeFromDb = serviceTypeRepository.findByIdAndIsDeletedFalse(serviceType.getId())
        .orElseThrow(() -> new ServiceTypeNotFoundException("ServiceType with id: " + serviceType.getId() + " not found"));

    serviceTypeFromDb.setNameDe(serviceType.getNameDe());
    serviceTypeFromDb.setNameHr(serviceType.getNameHr());
    serviceTypeFromDb.setNameIt(serviceType.getNameIt());
    serviceTypeFromDb.setNameEn(serviceType.getNameEn());

    serviceTypeRepository.save(serviceTypeFromDb);
  }

  @Override
  public int create(ServiceType serviceType) {
    ServiceType savedServiceType = serviceTypeRepository.save(serviceType);
    return savedServiceType.getId();
  }

  @Override
  public void delete(int id) {
    ServiceType serviceTypeFromDb = serviceTypeRepository.findByIdAndIsDeletedFalse(id)
        .orElseThrow(() -> new ServiceTypeNotFoundException("ServiceType with id: " + id + " not found"));

      serviceTypeFromDb.setDeleted(true);
      serviceTypeFromDb.getServices().forEach(service -> service.setDeleted(true));

      serviceTypeRepository.save(serviceTypeFromDb);

  }
}
