package com.adeo.services.service.auth;

import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.ZoneOffset;
import org.springframework.util.StringUtils;

public class AuthenticationToken {

    private static final String AUTHENTICATION_TOKEN_PREFIX = "Bearer ";

    private final String authenticationToken;
    private LocalDateTime expiresAt;

    public AuthenticationToken(String authenticationToken) {
        this.authenticationToken = authenticationToken;
    }

    public AuthenticationToken(String authenticationToken, LocalDateTime expiresAt) {
        this.authenticationToken = authenticationToken;
        this.expiresAt = expiresAt;
    }

    public String asRaw() {
      if (StringUtils.hasText(authenticationToken) && authenticationToken.startsWith(AUTHENTICATION_TOKEN_PREFIX)) {
        return authenticationToken.substring(7);
      } else {
        return authenticationToken;
      }
    }

    public boolean isTokenInValidFormat() {
        return StringUtils.hasText(authenticationToken) && this.authenticationToken.startsWith(AUTHENTICATION_TOKEN_PREFIX);
    }

    public boolean isExpired() {
        if (expiresAt == null) {
            throw new IllegalArgumentException("ExpiresAt is null");
        }
        return expiresAt.isBefore(LocalDateTime.now(ZoneId.of("CET")));
    }

}