package com.adeo.services.repository;

import com.adeo.services.entity.Menu;
import java.util.List;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;

public interface MenuRepository extends JpaRepository<Menu, Integer> {

  Optional<Menu> findByIdAndIsDeletedFalse(Integer id);
  List<Menu> findByIsDeletedFalse();

}
