package com.ote.user.controller;

import com.ote.user.service.CredentialCheckerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v1/credentials")
public class UserCredentialRestController {

    /*@Autowired
    private CredentialCheckerService credentialCheckerService;*/

    @GetMapping
    public boolean checkCredentials(@RequestParam("user") String user, @RequestParam("password") String password) {
        //return credentialCheckerService.checkCredentialsEncrypted(user, password);
        return true;
    }
}
