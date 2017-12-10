package com.ote.user.security.remoteprovider;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponentsBuilder;

import java.util.Collections;

@Service
public class RemoteAuthenticationProvider implements AuthenticationProvider {

    @Value("${user-service.url}")
    private String userServiceUrl;

    @Value("${user-service.endpoints.credentials}")
    private String userServiceCredentialsEndpoint;

    @Autowired
    private RestTemplate restTemplate;

    @Override
    public Authentication authenticate(Authentication auth) throws AuthenticationException {
        String username = auth.getName();
        String password = auth.getCredentials().toString();

        String url = UriComponentsBuilder.fromHttpUrl(userServiceUrl).
                path(userServiceCredentialsEndpoint).
                queryParam("user", username).
                queryParam("password", password).
                build().encode().toUriString();

        boolean isPasswordCorrect = restTemplate.getForObject(url, UserCredentialPayload.class).isPasswordCorrect();

        if (isPasswordCorrect) {
            return new UsernamePasswordAuthenticationToken(username, password, Collections.emptyList());
        }
        throw new BadCredentialsException("External system authentication failed");
    }

    @Override
    public boolean supports(Class<?> auth) {
        return auth.equals(UsernamePasswordAuthenticationToken.class);
    }
}