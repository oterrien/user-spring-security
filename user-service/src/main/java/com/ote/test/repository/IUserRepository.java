package com.ote.test.repository;

import org.springframework.security.core.userdetails.UserDetails;

public interface IUserRepository {

    UserDetails getUserDetails(String userName);
}
