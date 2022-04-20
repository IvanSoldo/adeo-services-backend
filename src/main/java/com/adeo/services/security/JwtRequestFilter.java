package com.adeo.services.security;

import com.adeo.services.service.auth.AuthenticationToken;
import com.adeo.services.service.auth.TokenProvider;
import java.io.IOException;
import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.filter.OncePerRequestFilter;

public class JwtRequestFilter extends OncePerRequestFilter {

  private static final String AUTHENTICATION_TOKEN_HEADER_NAME = "Authorization";

  private final TokenProvider tokenProvider;

  public JwtRequestFilter(TokenProvider tokenProvider) {
    this.tokenProvider = tokenProvider;
  }

  @Override
  protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain)
      throws ServletException, IOException {

    AuthenticationToken authenticationToken = new AuthenticationToken(
        request.getHeader(AUTHENTICATION_TOKEN_HEADER_NAME));

    if (authenticationToken.isTokenInValidFormat() && tokenProvider.isTokenValid(authenticationToken)) {
      Authentication authentication = tokenProvider.getAuthentication(authenticationToken);
      SecurityContextHolder.getContext().setAuthentication(authentication);
    }

    filterChain.doFilter(request, response);
  }

}