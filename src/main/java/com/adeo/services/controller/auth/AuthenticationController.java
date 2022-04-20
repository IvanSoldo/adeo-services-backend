package com.adeo.services.controller.auth;

import com.adeo.services.controller.AdeoErrorMessage;
import com.adeo.services.service.auth.AuthenticationService;
import com.adeo.services.service.auth.AuthenticationToken;
import com.adeo.services.service.auth.InvalidCredentialsException;
import java.security.Principal;
import javax.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.security.access.annotation.Secured;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class AuthenticationController {

  private final AuthenticationService authenticationService;

  @Autowired
  public AuthenticationController(AuthenticationService authenticationService) {
    this.authenticationService = authenticationService;
  }

  @PostMapping("api/v1/authentication/token-create")
  public AuthenticationTokenResponse createAuthenticationToken(
      @Valid @RequestBody AuthenticationTokenRequest authenticationTokenRequest) {

    AuthenticationToken authenticationToken = authenticationService.create(authenticationTokenRequest.getUsername(), authenticationTokenRequest.getPassword());

    return new AuthenticationTokenResponse(authenticationToken);
  }


  @Secured({"ROLE_ADMIN", "ROLE_RECEPTIONIST"})
  @GetMapping("api/v1/authentication/token-refresh")
  public AuthenticationTokenResponse getNewAuthenticationToken(Principal principal) {

    AuthenticationToken authenticationToken = authenticationService.createIfAuthenticated(principal.getName());

    return new AuthenticationTokenResponse(authenticationToken);
  }

  @ExceptionHandler(InvalidCredentialsException.class)
  @ResponseStatus(HttpStatus.UNAUTHORIZED)
  public AdeoErrorMessage handleInvalidCredentialsException(InvalidCredentialsException e) {
    return new AdeoErrorMessage(e.getMessage());
  }
}