package com.adeo.services.repository;

import com.adeo.services.entity.Order;
import com.adeo.services.entity.Service;
import java.time.LocalDateTime;
import javax.persistence.criteria.Join;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;
import javax.persistence.criteria.Subquery;
import org.springframework.data.jpa.domain.Specification;

public class ServiceSpecifications {

  public static Specification<Service> getByServiceTypeId(int serviceTypeId) {
    return (root, query, criteriaBuilder) -> criteriaBuilder.equal(root.get("serviceType"), serviceTypeId);
  }

  public static Specification<Service> getByEnabledTrue() {
    return (root, query, criteriaBuilder) -> criteriaBuilder.equal(root.get("isEnabled"), true);
  }

  public static Specification<Service> getByDeletedFalse() {
    return (root, query, criteriaBuilder) -> criteriaBuilder.equal(root.get("isDeleted"), false);
  }

  public static Specification<Service> getByServiceTypeDeletedFalse() {
    return (root, query, criteriaBuilder) -> criteriaBuilder.equal(root.get("serviceType").get("isDeleted"), false);
  }

  public static Specification<Service> getByName(String name) {
    return (root, query, criteriaBuilder) -> criteriaBuilder.equal(root.get("name"), name);
  }

  public static Specification<Service> isNotBooked(LocalDateTime startAt, LocalDateTime endAt) {
    return (root, query, criteriaBuilder) -> {
      Subquery<Integer> subQuery = query.subquery(Integer.class);
      Root<Service> serviceRoot = subQuery.from(Service.class);
      Join<Service, Order> serviceOrder = serviceRoot.join("orders");

      Predicate isOrderAfterRequestedOrder = criteriaBuilder.greaterThanOrEqualTo(serviceOrder.get("startAt"), endAt);
      Predicate isOrderBeforeRequestedOrder = criteriaBuilder.lessThanOrEqualTo(serviceOrder.get("endAt"), startAt);

      subQuery.select(serviceRoot.get("id"))
          .where(criteriaBuilder.not(criteriaBuilder.or(isOrderAfterRequestedOrder, isOrderBeforeRequestedOrder)));

      return criteriaBuilder.in(root.get("id")).value(subQuery).not();
    };
  }

}