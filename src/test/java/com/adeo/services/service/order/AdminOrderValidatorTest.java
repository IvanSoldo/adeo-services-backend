package com.adeo.services.service.order;

import static org.assertj.core.api.Assertions.assertThat;

import com.adeo.services.entity.Order;
import java.time.LocalDateTime;
import java.time.ZoneId;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class AdminOrderValidatorTest {

  private OrderValidator orderValidator;

  @BeforeEach
  void setUp() {
    orderValidator = new AdminOrderValidator();
  }

  @Test
  void given_Valid_Order_Then_Return_True() {
    // prepare
    Order order = new Order();
    LocalDateTime startAt = LocalDateTime.now(ZoneId.of("CET"));
    LocalDateTime endAt = LocalDateTime.now(ZoneId.of("CET")).plusHours(1);
    order.setStartAt(startAt);
    order.setEndAt(endAt);

    // execute
    boolean isOrderValid = orderValidator.isValid(order);

    // verify
    assertThat(isOrderValid).isTrue();
  }

  @Test
  void given_Invalid_Order_When_StartAt_Is_After_EndAt_Then_Return_False() {
    // prepare
    Order order = new Order();
    LocalDateTime startAt = LocalDateTime.now(ZoneId.of("CET")).plusHours(2);
    LocalDateTime endAt = LocalDateTime.now(ZoneId.of("CET"));
    order.setStartAt(startAt);
    order.setEndAt(endAt);

    // execute
    boolean isOrderValid = orderValidator.isValid(order);

    // verify
    assertThat(isOrderValid).isFalse();
  }

}