package com.adeo.services.repository;

import com.adeo.services.entity.ServiceType;
import java.util.List;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ServiceTypeRepository extends JpaRepository<ServiceType, Integer> {
    Optional<ServiceType> findByIdAndIsDeletedFalse(int id);

    List<ServiceType> findByIsDeletedFalse();
}
