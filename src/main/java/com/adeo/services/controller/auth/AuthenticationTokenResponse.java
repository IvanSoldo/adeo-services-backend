package com.adeo.services.controller.auth;

import com.adeo.services.service.auth.AuthenticationToken;

public class AuthenticationTokenResponse {

    private String authenticationToken;

    public AuthenticationTokenResponse() {
    }

    public AuthenticationTokenResponse(AuthenticationToken authenticationToken) {
        this.authenticationToken = authenticationToken.asRaw();
    }

    public String getAuthenticationToken() {
        return authenticationToken;
    }

    public void setAuthenticationToken(String authenticationToken) {
        this.authenticationToken = authenticationToken;
    }
}