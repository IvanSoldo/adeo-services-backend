package com.adeo.services.service.firewall;

import static org.assertj.core.api.Assertions.assertThat;

import java.util.List;
import org.junit.jupiter.api.Test;

class FirewallServiceImplTest {

  private FirewallService firewallService;

  @Test
  void given_Allowed_IpAddres_And_Security_Enabled_Then_Return_True() {
    // prepare
    AllowedIpAddressConfig allowedIpAddressConfig = new AllowedIpAddressConfig();
    allowedIpAddressConfig.setAddresses(List.of("192.168.11", "199.222.555.4"));

    Boolean isFirewallEnabled = true;
    firewallService = new FirewallServiceImpl(allowedIpAddressConfig, isFirewallEnabled);

    // execute
    boolean isIpAddressAllowed = firewallService.hasPermission(new IpAddress("192.168.11"));

    // verify
    assertThat(isIpAddressAllowed).isTrue();
  }

  @Test
  void given_Allowed_IpAddres_And_Security_Disabled_Then_Return_True() {
    // prepare
    AllowedIpAddressConfig allowedIpAddressConfig = new AllowedIpAddressConfig();
    allowedIpAddressConfig.setAddresses(List.of("192.168.11", "199.222.555.4"));

    Boolean isFirewallEnabled = false;
    firewallService = new FirewallServiceImpl(allowedIpAddressConfig, isFirewallEnabled);

    // execute
    boolean isIpAddressAllowed = firewallService.hasPermission(new IpAddress("192.168.11"));

    // verify
    assertThat(isIpAddressAllowed).isTrue();
  }

  @Test
  void given_Non_Allowed_IpAddres_And_Security_Enabled_Then_Return_False() {
    // prepare
    AllowedIpAddressConfig allowedIpAddressConfig = new AllowedIpAddressConfig();
    allowedIpAddressConfig.setAddresses(List.of("192.168.11", "199.222.555.4"));

    Boolean isFirewallEnabled = true;
    firewallService = new FirewallServiceImpl(allowedIpAddressConfig, isFirewallEnabled);

    // execute
    boolean isIpAddressAllowed = firewallService.hasPermission(new IpAddress("111.222.114"));

    // verify
    assertThat(isIpAddressAllowed).isFalse();
  }

  @Test
  void given_Non_Allowed_IpAddres_And_Security_Disabled_Then_Return_True() {
    // prepare
    AllowedIpAddressConfig allowedIpAddressConfig = new AllowedIpAddressConfig();
    allowedIpAddressConfig.setAddresses(List.of("192.168.11", "199.222.555.4"));

    Boolean isFirewallEnabled = false;
    firewallService = new FirewallServiceImpl(allowedIpAddressConfig, isFirewallEnabled);

    // execute
    boolean isIpAddressAllowed = firewallService.hasPermission(new IpAddress("111.222.114"));

    // verify
    assertThat(isIpAddressAllowed).isTrue();
  }

}