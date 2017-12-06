package com.ote.user.api;

import com.ote.user.business.UserRightServiceFactory;
import lombok.Getter;

public final class UserRightServiceProvider {

    @Getter
    public static final UserRightServiceProvider Instance = new UserRightServiceProvider();

    @Getter
    public final UserRightServiceFactory factory;

    private UserRightServiceProvider() {
        this.factory = new UserRightServiceFactory();
    }
}
