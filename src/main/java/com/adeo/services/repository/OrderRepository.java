package com.adeo.services.repository;

import com.adeo.services.entity.Order;


import java.time.LocalDateTime;
import java.util.List;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import org.springframework.data.repository.PagingAndSortingRepository;

public interface OrderRepository extends PagingAndSortingRepository<Order, Integer> {

  boolean existsByRoomUuid(String uuid);

  Page<Order> findAll(Pageable pageable);

  Page<Order> findByEndAtLessThanEqual(LocalDateTime endAt, Pageable pageable);

  Page<Order> findByStartAtGreaterThanEqual(LocalDateTime endAt, Pageable pageable);

  Page<Order> findByStartAtLessThanEqualAndEndAtGreaterThanEqual(LocalDateTime startAt, LocalDateTime endAt,
      Pageable pageable);

  int countByEndAtLessThanEqual(LocalDateTime endAt);

  int countByStartAtGreaterThanEqual(LocalDateTime endAt);

  int countByStartAtLessThanEqualAndEndAtGreaterThanEqual(LocalDateTime startAt, LocalDateTime endAt);

  int countByCreatedAtBetween(LocalDateTime createdAtStartDay, LocalDateTime createdAtEndDay);

  List<Order> findDistinctByEndAtBetweenAndServicesServiceTypeIdAndServicesIsDeletedFalseAndServicesIsEnabledTrue(
      LocalDateTime startAt, LocalDateTime endAt, int serviceTypeId);
}