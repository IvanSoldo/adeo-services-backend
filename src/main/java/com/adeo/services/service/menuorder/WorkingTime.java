package com.adeo.services.service.menuorder;

import java.time.LocalTime;

public class WorkingTime {

  private final LocalTime startOfShift;
  private final LocalTime endOfShift;

  public WorkingTime(LocalTime startOfShift, LocalTime endOfShift) {
    this.startOfShift = startOfShift;
    this.endOfShift = endOfShift;
  }

  public LocalTime getStartOfShift() {
    return startOfShift;
  }

  public LocalTime getEndOfShift() {
    return endOfShift;
  }
}
