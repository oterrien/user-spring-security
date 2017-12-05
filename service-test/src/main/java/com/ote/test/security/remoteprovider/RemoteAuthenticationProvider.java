package com.ote.test.security.remoteprovider;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponentsBuilder;

import java.math.BigInteger;
import java.security.MessageDigest;
import java.util.Collections;

@Service
public class RemoteAuthenticationProvider implements AuthenticationProvider {

    @Autowired
    private RestTemplate restTemplate;

    @Override
    public Authentication authenticate(Authentication auth) throws AuthenticationException {
        String username = auth.getName();
        String password = auth.getCredentials().toString();

        String url = UriComponentsBuilder.fromHttpUrl("http://localhost:8081").
                path("/api/v1/credentials").
                queryParam("user", username).
                queryParam("password", encryptPassword(password)).build().encode().toUriString();

        boolean areCredentialsCorrect = restTemplate.getForObject(url, Boolean.class);

        if (areCredentialsCorrect) {
            return new UsernamePasswordAuthenticationToken(username, password, Collections.emptyList());
        }
        throw new BadCredentialsException("External system authentication failed");
    }

    private static String encryptPassword(String password) {

        try {
            MessageDigest crypt = MessageDigest.getInstance("SHA-1");
            crypt.reset();
            crypt.update(password.getBytes("UTF-8"));

            return new BigInteger(1, crypt.digest()).toString(16);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public boolean supports(Class<?> auth) {
        return auth.equals(UsernamePasswordAuthenticationToken.class);
    }
}