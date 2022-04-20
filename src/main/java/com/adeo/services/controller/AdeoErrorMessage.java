package com.adeo.services.controller;

public class AdeoErrorMessage {

  private final String errorMessage;

  public AdeoErrorMessage(String errorMessage) {
    this.errorMessage = errorMessage;
  }

  public String getErrorMessage() {
    return errorMessage;
  }
}
