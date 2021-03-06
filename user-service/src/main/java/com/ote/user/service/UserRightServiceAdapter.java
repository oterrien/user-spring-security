package com.ote.user.service;

import com.ote.user.rights.api.IUserRightService;
import com.ote.user.rights.api.PerimeterPath;
import com.ote.user.rights.api.UserRightServiceProvider;
import com.ote.user.rights.api.exception.ApplicationNotFoundException;
import com.ote.user.rights.api.exception.RoleNotFoundException;
import com.ote.user.rights.api.exception.UserNotFoundException;
import com.ote.user.rights.spi.IUserRightRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
@Slf4j
public class UserRightServiceAdapter implements IUserRightService {

    public final IUserRightService userRightService;

    public UserRightServiceAdapter(@Autowired IUserRightRepository userRightRepository) {
        this.userRightService = UserRightServiceProvider.getInstance().getFactory().createService(userRightRepository);
    }

    @Override
    public boolean doesUserOwnPrivilegeForApplicationOnPerimeter(String user, String application, PerimeterPath perimeterPath, String privilege) {
        try {
            return userRightService.doesUserOwnPrivilegeForApplicationOnPerimeter(user, application, perimeterPath, privilege);
        } catch (UserNotFoundException | ApplicationNotFoundException | RoleNotFoundException e) {
            log.debug(e.getMessage(), e);
            return false;
        }
    }
}
