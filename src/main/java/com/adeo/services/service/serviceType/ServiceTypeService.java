package com.adeo.services.service.serviceType;

import com.adeo.services.entity.ServiceType;
import java.util.List;

public interface ServiceTypeService {

  List<ServiceType> getAll();

  void update(ServiceType serviceType);

  int create(ServiceType service);

  void delete(int id);

}
