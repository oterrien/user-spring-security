package com.ote.user.spi;

import com.ote.user.api.Perimeter;

import java.util.List;

public interface IUserRightRepository {

    boolean isUserDefined(String user);

    boolean isApplicationDefined(String application);

    boolean isRoleDefined(String user, String application);

    List<Perimeter> getPerimeters(String user, String application);
}
