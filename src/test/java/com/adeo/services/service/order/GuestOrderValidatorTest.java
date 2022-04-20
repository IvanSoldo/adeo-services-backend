package com.adeo.services.service.order;

import static org.assertj.core.api.Assertions.assertThat;

import com.adeo.services.entity.Order;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.ZoneId;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class GuestOrderValidatorTest {

  private OrderValidator orderValidator;

  @BeforeEach
  void setUp() {
    orderValidator = new GuestOrderValidator();
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
  void given_Valid_Order_When_Is_Whole_Day_Then_Return_True() {
    // prepare
    Order order = new Order();
    LocalDateTime startAt = LocalDate.now(ZoneId.of("CET")).atStartOfDay();
    LocalDateTime endAt = startAt.plusHours(23).plusMinutes(59);
    order.setStartAt(startAt);
    order.setEndAt(endAt);

    // execute
    boolean isOrderValid = orderValidator.isValid(order);

    // verify
    assertThat(isOrderValid).isTrue();
  }

  @Test
  void given_Invalid_Order_When_StartAt_And_EndAt_Same_Time_Then_Return_False() {
    // prepare
    Order order = new Order();
    LocalDateTime startAt = LocalDateTime.now(ZoneId.of("CET"));
    LocalDateTime endAt = LocalDateTime.now(ZoneId.of("CET"));
    order.setStartAt(startAt);
    order.setEndAt(endAt);

    // execute
    boolean isOrderValid = orderValidator.isValid(order);

    // verify
    assertThat(isOrderValid).isFalse();
  }

  @Test
  void given_Invalid_Order_When_StartAt_And_EndAt_Are_In_Past_Then_Return_False() {
    // prepare
    Order order = new Order();
    LocalDateTime startAt = LocalDateTime.now(ZoneId.of("CET")).minusHours(2);
    LocalDateTime endAt = LocalDateTime.now(ZoneId.of("CET")).minusHours(1);
    order.setStartAt(startAt);
    order.setEndAt(endAt);

    // execute
    boolean isOrderValid = orderValidator.isValid(order);

    // verify
    assertThat(isOrderValid).isFalse();
  }

  @Test
  void given_Invalid_Order_When_StartAt_Is_After_EndAt_In_Future_Then_Return_False() {
    // prepare
    Order order = new Order();
    LocalDateTime startAt = LocalDateTime.now(ZoneId.of("CET")).plusHours(2);
    LocalDateTime endAt = LocalDateTime.now(ZoneId.of("CET")).plusHours(1);
    order.setStartAt(startAt);
    order.setEndAt(endAt);

    // execute
    boolean isOrderValid = orderValidator.isValid(order);

    // verify
    assertThat(isOrderValid).isFalse();
  }

  @Test
  void given_Invalid_Order_When_StartAt_Is_After_EndAt_In_Past_Then_Return_False() {
    // prepare
    Order order = new Order();
    LocalDateTime startAt = LocalDateTime.now(ZoneId.of("CET")).minusHours(1);
    LocalDateTime endAt = LocalDateTime.now(ZoneId.of("CET")).minusHours(2);
    order.setStartAt(startAt);
    order.setEndAt(endAt);

    // execute
    boolean isOrderValid = orderValidator.isValid(order);

    // verify
    assertThat(isOrderValid).isFalse();
  }

  @Test
  void given_Invalid_Order_When_StartAt_And_EndAt_Is_Day_Before_Current_Then_Return_True() {
    // prepare
    Order order = new Order();
    LocalDateTime startAt = LocalDateTime.now(ZoneId.of("CET")).minusDays(1);
    LocalDateTime endAt = LocalDateTime.now(ZoneId.of("CET")).minusDays(1).plusHours(1);
    order.setStartAt(startAt);
    order.setEndAt(endAt);

    // execute
    boolean isOrderValid = orderValidator.isValid(order);

    // verify
    assertThat(isOrderValid).isFalse();
  }

  @Test
  void given_Invalid_Order_When_StartAt_And_EndAt_Is_Day_After_Current_Then_Return_True() {
    // prepare
    Order order = new Order();
    LocalDateTime startAt = LocalDateTime.now(ZoneId.of("CET")).plusDays(2);
    LocalDateTime endAt = LocalDateTime.now(ZoneId.of("CET")).plusDays(2).plusHours(1);
    order.setStartAt(startAt);
    order.setEndAt(endAt);

    // execute
    boolean isOrderValid = orderValidator.isValid(order);

    // verify
    assertThat(isOrderValid).isFalse();
  }

  @Test
  void given_Invalid_Order_When_StartAt_And_EndAt_Is_Month_Before_Current_Then_Return_True() {
    // prepare
    Order order = new Order();
    LocalDateTime startAt = LocalDateTime.now(ZoneId.of("CET")).minusMonths(1);
    LocalDateTime endAt = LocalDateTime.now(ZoneId.of("CET")).minusMonths(1).plusHours(1);
    order.setStartAt(startAt);
    order.setEndAt(endAt);

    // execute
    boolean isOrderValid = orderValidator.isValid(order);

    // verify
    assertThat(isOrderValid).isFalse();
  }

  @Test
  void given_Invalid_Order_When_StartAt_And_EndAt_Is_Month_After_Current_Then_Return_True() {
    // prepare
    Order order = new Order();
    LocalDateTime startAt = LocalDateTime.now(ZoneId.of("CET")).plusMonths(1);
    LocalDateTime endAt = LocalDateTime.now(ZoneId.of("CET")).plusMonths(1).plusHours(1);
    order.setStartAt(startAt);
    order.setEndAt(endAt);

    // execute
    boolean isOrderValid = orderValidator.isValid(order);

    // verify
    assertThat(isOrderValid).isFalse();
  }

  @Test
  void given_Invalid_Order_When_StartAt_And_EndAt_Is_Year_Before_Current_Then_Return_True() {
    // prepare
    Order order = new Order();
    LocalDateTime startAt = LocalDateTime.now(ZoneId.of("CET")).minusYears(1);
    LocalDateTime endAt = LocalDateTime.now(ZoneId.of("CET")).minusYears(1).plusHours(1);
    order.setStartAt(startAt);
    order.setEndAt(endAt);

    // execute
    boolean isOrderValid = orderValidator.isValid(order);

    // verify
    assertThat(isOrderValid).isFalse();
  }

  @Test
  void given_Invalid_Order_When_StartAt_And_EndAt_Is_Year_After_Current_Then_Return_True() {
    // prepare
    Order order = new Order();
    LocalDateTime startAt = LocalDateTime.now(ZoneId.of("CET")).plusYears(1);
    LocalDateTime endAt = LocalDateTime.now(ZoneId.of("CET")).plusYears(1).plusHours(1);
    order.setStartAt(startAt);
    order.setEndAt(endAt);

    // execute
    boolean isOrderValid = orderValidator.isValid(order);

    // verify
    assertThat(isOrderValid).isFalse();
  }

}