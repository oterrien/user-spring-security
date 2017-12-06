package com.ote.user.business;

import com.ote.user.api.IUserRightService;
import com.ote.user.api.Perimeter;
import com.ote.user.api.PerimeterPath;
import com.ote.user.api.exception.ApplicationNotFoundException;
import com.ote.user.api.exception.RoleNotFoundException;
import com.ote.user.api.exception.UserNotFoundException;
import com.ote.user.spi.IUserRightRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

import java.util.List;
import java.util.Objects;

@Slf4j
@RequiredArgsConstructor
class UserRightService implements IUserRightService {

    private final IUserRightRepository userRightRepository;

    @Override
    public boolean doesUserOwnPrivilegeForApplicationOnPerimeter(String user, String application, PerimeterPath perimeterPath, String privilege)
            throws UserNotFoundException, ApplicationNotFoundException, RoleNotFoundException {

        if (!userRightRepository.isUserDefined(user)) {
            throw new UserNotFoundException(user);
        }

        if (!userRightRepository.isApplicationDefined(application)) {
            throw new ApplicationNotFoundException(application);
        }

        if (!userRightRepository.isRoleDefined(user, application)) {
            throw new RoleNotFoundException(user, application);
        }

        List<Perimeter> perimeters = userRightRepository.getPerimeters(user, application);
        perimeters.removeIf(p -> p.getPrivileges().isEmpty());

        if (perimeters.isEmpty()) {
            throw new RoleNotFoundException(user, application, perimeterPath);
        }

        return perimeters.stream().
                filter(p -> Objects.equals(p.getCode(), perimeterPath.toString())).
                flatMap(p -> p.getPrivileges().stream()).
                anyMatch(p -> Objects.equals(p, privilege));
    }
}
