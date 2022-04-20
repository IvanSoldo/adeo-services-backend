package com.adeo.services.service.order;

import com.adeo.services.entity.Order;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.ZoneId;

public class GuestOrderValidator implements OrderValidator {

  @Override
  public boolean isValid(Order order) {

    if (isWholeDayToday(order)) {
      return true;
    } else if (isStartAtAfterEndAt(order)) {
      return false;
    } else if (isStartAtAndEndAtEqual(order)) {
      return false;
    } else if (isInPast(order)) {
      return false;
    } else {
      return !isInFutureTwoOrMoreDays(order);
    }

  }

  private boolean isInFutureTwoOrMoreDays(Order order) {
    LocalDateTime twoDaysInFuture = LocalDate.now(ZoneId.of("CET")).atStartOfDay().plusDays(2);
    return order.getStartAt().isAfter(twoDaysInFuture) || order.getEndAt().isAfter(twoDaysInFuture);
  }

  private boolean isInPast(Order order) {
    LocalDateTime currentDayTime = LocalDateTime.now(ZoneId.of("CET"));
    return order.getStartAt().isBefore(currentDayTime) || order.getEndAt().isBefore(currentDayTime);
  }

  private boolean isWholeDayToday(Order order) {
    LocalDateTime todayAtStartOfDay = LocalDate.now(ZoneId.of("CET")).atStartOfDay();
    LocalDateTime todayAtEndOfDay = todayAtStartOfDay.plusHours(23).plusMinutes(59);

    return order.getStartAt().equals(todayAtStartOfDay) && order.getEndAt().equals(todayAtEndOfDay);
  }

  private boolean isStartAtAndEndAtEqual(Order order) {
    return order.getStartAt().equals(order.getEndAt());
  }

  private boolean isStartAtAfterEndAt(Order order) {
    return order.getStartAt().isAfter(order.getEndAt());
  }
}
