package com.ote.test.service;

import com.ote.test.repository.IUserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;

import java.math.BigInteger;
import java.security.MessageDigest;
import java.util.Objects;

@Service
public class CredentialCheckerService {

    @Autowired
    private IUserRepository userRepository;

    public boolean checkCredentials(String username, String password) {
        UserDetails userDetails = userRepository.getUserDetails(username);
        return (userDetails != null && Objects.equals(userDetails.getPassword(), password));
    }

    public boolean checkCredentialsEncrypted(String username, String password) {
        UserDetails userDetails = userRepository.getUserDetails(username);
        return (userDetails != null && Objects.equals(encryptPassword(userDetails.getPassword()), password));
    }

    private static String encryptPassword(String password) {

        try {
            MessageDigest crypt = MessageDigest.getInstance("SHA-1");
            crypt.reset();
            crypt.update(password.getBytes("UTF-8"));

            return new BigInteger(1, crypt.digest()).toString(16);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

}
