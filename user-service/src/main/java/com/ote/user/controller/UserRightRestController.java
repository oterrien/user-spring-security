package com.ote.user.controller;

import com.ote.user.persistence.model.UserRightEntity;
import com.ote.user.persistence.repository.IUserRightJpaRepository;
import com.ote.user.rights.api.IUserRightService;
import com.ote.user.rights.api.PerimeterPath;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/rights")
@Slf4j
public class UserRightRestController {

    @Autowired
    private IUserRightService userRightService;

    @GetMapping(value = "/isGranted")
    @ResponseBody
    @ResponseStatus(HttpStatus.OK)
    public boolean doesUserOwnPrivilegeForApplicationOnPerimeter(@RequestParam("user") String user,
                                                                 @RequestParam("application") String application,
                                                                 @RequestParam("perimeter") String perimeter,
                                                                 @RequestParam("privilege") String privilege) throws Exception {

        log.debug(String.format("IsGranted?user=%s&application=%s&perimeter=%s&privilege=%s", user, application, perimeter, privilege));

        PerimeterPath perimeterPath = new PerimeterPath.Parser(perimeter).parse();
        return userRightService.doesUserOwnPrivilegeForApplicationOnPerimeter(user, application, perimeterPath, privilege);
    }

    //region TEMP
    @Autowired
    private IUserRightJpaRepository userRightJpaRepository;

    @GetMapping
    @ResponseBody
    @ResponseStatus(HttpStatus.OK)
    public List<UserRightEntity> getUserRights(@RequestParam("user") String user,
                                               @RequestParam("application") String application) {
        return userRightJpaRepository.findByUserAndApplication(user, application);
    }
    //endregion

}
