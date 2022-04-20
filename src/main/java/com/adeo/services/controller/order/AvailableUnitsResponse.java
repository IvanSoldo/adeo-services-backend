package com.adeo.services.controller.order;

public class AvailableUnitsResponse {

  private final int availableUnits;

  private AvailableUnitsResponse(int availableUnits) {
    this.availableUnits = availableUnits;
  }

  public static AvailableUnitsResponse from(int availableUnits) {
    return new AvailableUnitsResponse(availableUnits);
  }

  public int getAvailableUnits() {
    return availableUnits;
  }
}
