package com.ote.user.api.exception;

public class ApplicationNotFoundException extends Exception {

    private static final String APP_NOT_FOUND_MESSAGE = "Application '%s' not found";

    public ApplicationNotFoundException(String application) {
        super(String.format(APP_NOT_FOUND_MESSAGE, application));
    }
}
