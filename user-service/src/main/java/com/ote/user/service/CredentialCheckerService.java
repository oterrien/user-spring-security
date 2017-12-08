package com.ote.user.service;

//@Service
public class CredentialCheckerService {

    /*@Autowired
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
    }*/

}
