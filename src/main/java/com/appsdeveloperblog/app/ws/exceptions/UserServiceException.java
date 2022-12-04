package com.appsdeveloperblog.app.ws.exceptions;


public class UserServiceException extends RuntimeException {

    private static final long serialVersionUID = 6835192693475021280L;

    public UserServiceException(String message) {
        super(message);
    }
}
