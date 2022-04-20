package com.adeo.services.service.firewall;

public class InvalidIpAddressException extends RuntimeException{

  public InvalidIpAddressException(String message) {
    super(message);
  }
}
