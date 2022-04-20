package com.adeo.services.service.firewall;

import java.util.List;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

@Service
public class FirewallServiceImpl implements FirewallService {

  private final List<IpAddress> allowedIpAddresses;
  private final Boolean isFirewallEnabled;

  public FirewallServiceImpl(AllowedIpAddressConfig allowedIpAddressConfig,
      @Value("${application.firewall.is-enabled}") Boolean isFirewallEnabled) {
    this.allowedIpAddresses = allowedIpAddressConfig.asIpAddresses();
    this.isFirewallEnabled = isFirewallEnabled;
  }

  @Override
  public boolean hasPermission(IpAddress userIpAddress) {
    if (!isFirewallEnabled) {
      return true;
    } else {
      return allowedIpAddresses.contains(userIpAddress);
    }
  }
}
