package com.adeo.services.service.firewall;

public interface FirewallService {

  boolean hasPermission(IpAddress userIpAddress);

}
