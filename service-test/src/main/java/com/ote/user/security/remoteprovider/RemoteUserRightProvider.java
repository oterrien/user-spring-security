package com.ote.user.security.remoteprovider;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponentsBuilder;

@Service
public class RemoteUserRightProvider {

    @Value("${application.name}")
    private String applicationName;

    @Value("${user-service.url}")
    private String userServiceUrl;

    @Value("${user-service.endpoints.rights}")
    private String userServiceRightsEndpoint;

    @Autowired
    private RestTemplate restTemplate;

    public boolean doesUserOwnPrivilegeForApplicationOnPerimeter(String user, String perimeter, String privilege) {

        String url = UriComponentsBuilder.
                fromHttpUrl(userServiceUrl).
                path(userServiceRightsEndpoint).
                queryParam("user", user).
                queryParam("application", applicationName).
                queryParam("perimeter", perimeter).
                queryParam("privilege", privilege).
                build().
                encode().
                toUriString();

        UserRightPayload result = restTemplate.getForObject(url, UserRightPayload.class);
        return result.isGranted();
    }
}
