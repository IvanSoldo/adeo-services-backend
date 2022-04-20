package com.adeo.services.repository;

import com.adeo.services.entity.MenuItem;
import java.util.List;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;

public interface MenuItemRepository extends JpaRepository<MenuItem, Integer> {

  Optional<MenuItem> findByIdAndIsDeletedFalse(Integer id);

  boolean existsByIdIn(List<Integer> ids);

}
