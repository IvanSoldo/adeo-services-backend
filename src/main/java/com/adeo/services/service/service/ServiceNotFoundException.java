package com.adeo.services.service.service;

public class ServiceNotFoundException extends RuntimeException {

  public ServiceNotFoundException(String message) {
    super(message);
  }
}
