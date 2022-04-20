package com.adeo.services.repository;

import com.adeo.services.entity.Service;

import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

public interface ServiceRepository extends JpaRepository<Service, Integer>, JpaSpecificationExecutor<Service> {

  Optional<Service> findByIdAndIsDeletedFalse(int id);

}
