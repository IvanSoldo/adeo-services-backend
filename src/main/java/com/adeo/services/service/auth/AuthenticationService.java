package com.adeo.services.service.auth;

public interface AuthenticationService {

    AuthenticationToken create(String username, String password);

    AuthenticationToken createIfAuthenticated(String username);
}