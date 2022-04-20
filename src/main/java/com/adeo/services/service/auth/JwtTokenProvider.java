package com.adeo.services.service.auth;

import com.adeo.services.entity.Role;
import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.exceptions.JWTCreationException;
import com.auth0.jwt.exceptions.JWTVerificationException;
import com.auth0.jwt.interfaces.Claim;
import com.auth0.jwt.interfaces.DecodedJWT;
import com.auth0.jwt.interfaces.JWTVerifier;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.ZonedDateTime;
import java.util.Date;
import java.util.List;
import java.util.concurrent.TimeUnit;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.stereotype.Component;

@Component
public class JwtTokenProvider implements TokenProvider {

  private static final String COMPANY_NAME = "ADEO";
  private static final String AUTHORITIES_CLAIM = "authorities";
  private static final String ROLE_PREFIX = "ROLE_";
  private final long expirationInMinutes;
  private final Algorithm algorithm;

  public JwtTokenProvider(@Value("${application.security.jwt-secret}") String secretKey,
      @Value("${application.security.jwt-expiration-time-in-miliseconds}") int expirationInMiliseconds) {
    this.expirationInMinutes = TimeUnit.MILLISECONDS.toMinutes(expirationInMiliseconds);
    this.algorithm = Algorithm.HMAC256(secretKey);
  }

  @Override
  public AuthenticationToken create(String username, Role role) {

    LocalDateTime jwtTokenExpiresAt = getExpirationDate();

    AuthenticationToken authenticationToken = null;

    try {
      String jwtToken = JWT.create()
          .withIssuer(COMPANY_NAME)
          .withSubject(username)
          .withIssuedAt(new Date())
          .withExpiresAt(convertLocalDateTimeToDate(jwtTokenExpiresAt))
          .withClaim(AUTHORITIES_CLAIM, role.name())
          .sign(algorithm);

      authenticationToken = new AuthenticationToken(jwtToken, jwtTokenExpiresAt);
    } catch (JWTCreationException exception) {

    }

    return authenticationToken;
  }

  @Override
  public boolean isTokenValid(AuthenticationToken authenticationToken) {

    try {
      verifyJwtToken(authenticationToken);
    } catch (JWTVerificationException exception) {
      return false;
    }

    return true;
  }

  private DecodedJWT verifyJwtToken(AuthenticationToken authenticationToken) {
    JWTVerifier verifier = JWT.require(algorithm)
        .withIssuer(COMPANY_NAME)
        .build();
    return verifier.verify(authenticationToken.asRaw());
  }

  @Override
  public Authentication getAuthentication(AuthenticationToken authenticationToken) {
    DecodedJWT decodedJWT = verifyJwtToken(authenticationToken);

    Claim claimAuthorities = decodedJWT.getClaim(AUTHORITIES_CLAIM);

    List<SimpleGrantedAuthority> authorities = List
        .of(new SimpleGrantedAuthority(ROLE_PREFIX + claimAuthorities.asString()));

    User principal = new User(decodedJWT.getSubject(),
        authenticationToken.asRaw(),
        authorities);

    return new UsernamePasswordAuthenticationToken(principal, decodedJWT.getToken(), authorities);
  }

  private LocalDateTime getExpirationDate() {
    return LocalDateTime.now(ZoneId.of("CET")).plusMinutes(expirationInMinutes);
  }

  private Date convertLocalDateTimeToDate(LocalDateTime localDateTime) {
    ZonedDateTime zonedDateTime = localDateTime.atZone(ZoneId.of("CET"));
    return Date.from(zonedDateTime.toInstant());
  }
}