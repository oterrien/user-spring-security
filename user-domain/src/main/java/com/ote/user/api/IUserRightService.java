package com.ote.user.api;

import com.ote.user.api.exception.ApplicationNotFoundException;
import com.ote.user.api.exception.RoleNotFoundException;
import com.ote.user.api.exception.UserNotFoundException;

public interface IUserRightService {

    boolean doesUserOwnPrivilegeForApplicationOnPerimeter(String user, String application, PerimeterPath perimeterPath, String privilege)
            throws UserNotFoundException, ApplicationNotFoundException, RoleNotFoundException;
}
