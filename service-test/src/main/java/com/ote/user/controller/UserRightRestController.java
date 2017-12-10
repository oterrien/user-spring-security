package com.ote.user.controller;

import org.springframework.http.HttpStatus;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/user-secured")
public class UserRightRestController {

    @PreAuthorize("hasPermission('DEAL/GLE', 'READ_WRITE')")
    @GetMapping
    @ResponseBody
    @ResponseStatus(HttpStatus.OK)
    public boolean test() {
        return true;
    }
}
