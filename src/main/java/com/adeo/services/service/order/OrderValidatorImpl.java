package com.adeo.services.service.order;

import com.adeo.services.entity.Order;
import java.time.LocalTime;
import java.time.ZoneId;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;

@Service
public class OrderValidatorImpl {

  public boolean checkIfOrderIsValid(Order order) {
    //Start at mora bit veci od end at - zato su tu slj dva checka
    if (order.getStartAt().isAfter(order.getEndAt())) {
      return false;
    }

    if (order.getStartAt().isEqual(order.getEndAt())) {
      return false;
    }

    LocalDateTime today = LocalDateTime.now(ZoneId.of("CET"));
    LocalDateTime tomorrow = today.plusDays(1);

    //order samo valja ako je danas ili sutra - zato sljedeca dva checka i zato ne mogu samo isAfter i isBefore koristit
    if (order.getStartAt().getDayOfYear() != today.getDayOfYear()
        && order.getStartAt().getDayOfYear() != tomorrow.getDayOfYear()) {
      return false;
    }

    if (order.getEndAt().getDayOfYear() != today.getDayOfYear()
        && order.getEndAt().getDayOfYear() != tomorrow.getDayOfYear()) {
      return false;
    }

    //order prolazi samo ak su isti dan pocetak i kraj
    if (order.getStartAt().getDayOfYear() != order.getEndAt().getDayOfYear()) {
      return false;
    }
    //po business req order ne smije bit u proslosti (eg: ako je sad 15:23, ne moze order startat u 14:00, ali moze u 15:00)
    //osim ako je cijeli dan - u tom slucaju se procesira order u cijelom danu
    boolean isWholeDay = this.isWholeDay(order);
    if (order.getStartAt().getHour() < today.getHour() && !isWholeDay && order.getStartAt().getDayOfYear() != tomorrow.getDayOfYear()) {
      return false;
    }

    return true;
  }

  private boolean isWholeDay(Order order) {
    LocalTime startAt = LocalTime.of(0, 0);
    LocalTime endAt = LocalTime.of(23, 59);

    return order.getStartAt().toLocalTime().equals(startAt) && order.getEndAt().toLocalTime().equals(endAt);
  }

}
