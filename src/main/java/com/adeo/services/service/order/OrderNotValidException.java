package com.adeo.services.service.order;

public class OrderNotValidException extends RuntimeException {

  public OrderNotValidException(String message) {
    super(message);
  }

}
