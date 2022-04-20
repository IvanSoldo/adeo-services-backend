package com.adeo.services.service.service;

import com.adeo.services.entity.Service;
import java.util.List;

public interface ServiceService {

  List<Service> getAll();

  void update(Service service);

  int create(Service service,Integer serviceTypeId);

  void delete(int id);

  List<Service> getByServiceTypeId(Integer serviceTypeId);
}