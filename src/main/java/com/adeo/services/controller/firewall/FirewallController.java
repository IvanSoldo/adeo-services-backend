package com.adeo.services.controller.firewall;

import com.adeo.services.service.firewall.FirewallService;
import com.adeo.services.service.firewall.IpAddress;
import javax.servlet.http.HttpServletRequest;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class FirewallController {

  private final FirewallService firewallService;

  public FirewallController(FirewallService firewallService) {
    this.firewallService = firewallService;
  }

  @GetMapping("api/v1/firewall")
  public ResponseEntity isIpAddressValid(HttpServletRequest request) {

    IpAddress ipAddress = new IpAddress(request.getRemoteAddr());

    boolean isIpAddressValid = firewallService.hasPermission(ipAddress);

    if (isIpAddressValid) {
      return new ResponseEntity(HttpStatus.OK);
    } else {
      return new ResponseEntity(HttpStatus.BAD_REQUEST);
    }

  }

}
