package com.ote.user.business;

import com.ote.user.api.IUserRightService;
import com.ote.user.spi.IUserRightRepository;

public class UserRightServiceFactory {

    public IUserRightService createService(IUserRightRepository userRightRepository) {
        return new UserRightService(userRightRepository);
    }

}
