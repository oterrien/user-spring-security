package com.ote.test.security;

import com.ote.test.service.CredentialCheckerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.stereotype.Service;

import java.util.Collections;

@Service
public class CustomAuthenticationProvider implements AuthenticationProvider {

    @Autowired
    private CredentialCheckerService credentialCheckerService;

    @Override
    public Authentication authenticate(Authentication auth) throws AuthenticationException {

        String username = auth.getName();
        String password = auth.getCredentials().toString();

        if (credentialCheckerService.checkCredentials(username, password)) {
            return new UsernamePasswordAuthenticationToken(username, password, Collections.emptyList());
        }
        throw new BadCredentialsException("External system authentication failed");
    }

    @Override
    public boolean supports(Class<?> auth) {
        return auth.equals(UsernamePasswordAuthenticationToken.class);
    }
}