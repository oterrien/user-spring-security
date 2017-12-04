package com.ote.test;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.stereotype.Service;

@Service
public class SampleService {

    @Autowired
    private UserDetailsService userDetailsService;

    //@Secured("ROLE_USER") //--> authorized
    //@PreAuthorize("hasRole('ROLE_USER')")  //--> authorized
    //@PreAuthorize("hasAuthority('ROLE_USER')")  //--> authorized
    //@PreAuthorize("hasRole('USER')") //--> denied
    //@PreAuthorize("hasAuthority('FOO_READ_PRIVILEGE')") //--> authorized
    //@PreAuthorize("hasPermission(#id, 'Foo', 'read')")
    @PreAuthorize("hasRole('ROLE_USER') and hasPermission('Foo', 'read')")
    public String secure() {
        return "#################### Authorized #####################";
    }

    //@PreAuthorize("hasPermission('DEAL', 'READ')")
    @PreAuthorize("hasPermission('DEAL*', 'WRITE')")
    //@PreAuthorize("hasPermission('DEAL/GLE', 'READ') or hasPermission('DEAL/GLE', 'WRITE')")
    public String secureForGlencore() {
        return "#################### Authorized #####################";
    }
}