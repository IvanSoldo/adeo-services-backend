package com.adeo.services.service.auth;

import com.adeo.services.entity.Account;
import com.adeo.services.repository.AccountRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class AuthenticationServiceImpl implements AuthenticationService {

    private final AccountRepository accountRepository;
    private final TokenProvider tokenProvider;
    private final PasswordEncoder passwordEncoder;

    @Autowired
    public AuthenticationServiceImpl(AccountRepository accountRepository,
                                     TokenProvider tokenProvider,
                                     PasswordEncoder passwordEncoder) {
        this.accountRepository = accountRepository;
        this.tokenProvider = tokenProvider;
        this.passwordEncoder = passwordEncoder;
    }

    @Override
    public AuthenticationToken create(String username, String password) {

        Account account = accountRepository.findByUsername(username).orElseThrow(() -> new InvalidCredentialsException("Invalid username or password"));

        if (!passwordEncoder.matches(password, account.getPassword())) {
            throw new InvalidCredentialsException("Invalid username or password");
        }

        return tokenProvider.create(account.getUsername(), account.getRole());
    }

    @Override
    public AuthenticationToken createIfAuthenticated(String username) {

        Account account = accountRepository.findByUsername(username).orElseThrow(() -> new InvalidCredentialsException("Invalid username or password"));

        return tokenProvider.create(account.getUsername(), account.getRole());
    }
}