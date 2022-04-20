package com.adeo.services.service.firewall;

import java.util.List;
import java.util.stream.Collectors;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

@Component
@ConfigurationProperties(prefix = "application.firewall")
public class AllowedIpAddressConfig {

  private List<String> addresses;

  public List<IpAddress> asIpAddresses() {
    return addresses.stream().map(IpAddress::new).collect(Collectors.toList());
  }

  public void setAddresses(List<String> addresses) {
    this.addresses = addresses;
  }
}
