package com.ote.test.controller;

import com.ote.test.service.UserRightService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/rights")
@Slf4j
public class UserRightRestController {

    @Autowired
    private UserRightService userRightService;

    @GetMapping(value = "/isGranted")
    @ResponseBody
    @ResponseStatus(HttpStatus.OK)
    public boolean doesUserOwnPrivilegeForApplicationOnPerimeter(@RequestParam("user") String user,
                                                                 @RequestParam("application") String application,
                                                                 @RequestParam("perimeter") String perimeter,
                                                                 @RequestParam("privilege") String privilege) {

        log.info(String.format("IsGranted?user=%s&application=%s&perimeter=%s&privilege=%s", user, application, perimeter, privilege));
        return userRightService.doesUserOwnPrivilegeForApplicationOnPerimeter(user, application, perimeter, privilege);
    }


}
