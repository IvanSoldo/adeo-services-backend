package com.adeo.services.service.firewall;

import java.util.Objects;

public class IpAddress {

  private final String ipAddress;

  public IpAddress(String ipAddress) {
    this.ipAddress = ipAddress;
  }

  @Override
  public boolean equals(Object o) {
    if (this == o) {
      return true;
    }
    if (o == null || getClass() != o.getClass()) {
      return false;
    }
    IpAddress ipAddress1 = (IpAddress) o;
    return Objects.equals(ipAddress, ipAddress1.ipAddress);
  }

  @Override
  public int hashCode() {
    return Objects.hash(ipAddress);
  }
}
