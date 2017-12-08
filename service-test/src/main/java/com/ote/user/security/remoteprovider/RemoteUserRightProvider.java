package com.ote.user.security.remoteprovider;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponentsBuilder;

import java.util.Base64;

@Service
public class RemoteUserRightProvider {

    @Value("${application.name}")
    private String applicationName;

    @Autowired
    private RestTemplate restTemplate;

    public boolean doesUserOwnPrivilegeForApplicationOnPerimeter(String user, String password, String perimeter, String privilege) {

        //http://localhost:8081/api/v1/rights/applications/APPLE/perimeters/DEAL/privileges/READ
        String url = UriComponentsBuilder.
                fromHttpUrl("http://localhost:8081").
                path("/api/v1/rights").
                path("/isGranted").
                queryParam("user", user).
                queryParam("application", applicationName).
                queryParam("perimeter", perimeter).
                queryParam("privilege", privilege).
                build().
                encode().
                toUriString();

        HttpHeaders headers = createHttpHeaders(user, password);
        HttpEntity<String> entity = new HttpEntity<>(headers);
        return restTemplate.exchange(url, HttpMethod.GET, entity, Boolean.class).getBody();
    }

    private HttpHeaders createHttpHeaders(String user, String password) {
        String notEncoded = user + ":" + password;
        String encodedAuth = Base64.getEncoder().encodeToString(notEncoded.getBytes());
        HttpHeaders headers = new HttpHeaders();
        headers.add("Authorization", "Basic " + encodedAuth);
        return headers;
    }
}
