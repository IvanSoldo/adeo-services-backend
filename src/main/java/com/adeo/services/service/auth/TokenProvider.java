package com.adeo.services.service.auth;

import com.adeo.services.entity.Role;
import org.springframework.security.core.Authentication;

public interface TokenProvider {

    AuthenticationToken create(String username, Role role);

    boolean isTokenValid(AuthenticationToken authenticationToken);

    Authentication getAuthentication(AuthenticationToken authenticationToken);

}