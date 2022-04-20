package com.adeo.services.service.menuorder;

import java.time.LocalTime;
import java.time.ZoneId;

public class FoodServiceWorkingTime {

  private final LocalTime currentTime = LocalTime.now(ZoneId.of("CET"));

  private final WorkingTime firstShiftWorkingTime;
  private final WorkingTime secondShiftWorkingTime;

  public FoodServiceWorkingTime(WorkingTime firstShiftWorkingTime,
      WorkingTime secondShiftWorkingTime) {
    this.firstShiftWorkingTime = firstShiftWorkingTime;
    this.secondShiftWorkingTime = secondShiftWorkingTime;
  }

  public boolean canOrderFood() {
    return isFirstShift() || isSecondShift();
  }

  private boolean isFirstShift() {
    return currentTime.isAfter(firstShiftWorkingTime.getStartOfShift()) && currentTime.isBefore(firstShiftWorkingTime.getEndOfShift());
  }

  private boolean isSecondShift() {
    return currentTime.isAfter(secondShiftWorkingTime.getStartOfShift()) && currentTime.isBefore(secondShiftWorkingTime.getEndOfShift());
  }

}
