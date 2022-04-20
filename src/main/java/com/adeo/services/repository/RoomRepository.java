package com.adeo.services.repository;

import com.adeo.services.entity.Room;
import java.util.List;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;

public interface RoomRepository extends JpaRepository<Room, Integer> {
  Optional<Room> findByUuidAndIsDeletedFalse(String uuid);

  boolean  existsByUuidAndIsDeletedFalse(String uuid);

  int removeByUuid(String uuid);

  List<Room> findAllByIsDeletedFalse();
}
