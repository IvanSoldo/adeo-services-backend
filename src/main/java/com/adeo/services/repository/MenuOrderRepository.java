package com.adeo.services.repository;

import com.adeo.services.entity.MenuOrder;
import java.util.Optional;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

public interface MenuOrderRepository extends JpaRepository<MenuOrder, Integer> {

  int countByIsProcessedFalse();

  Optional<MenuOrder> findById(Integer id);

  Page<MenuOrder> findByIsProcessedTrue(Pageable pageable);
  Page<MenuOrder> findByIsProcessedFalse(Pageable pageable);

}
