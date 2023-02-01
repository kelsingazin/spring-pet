package com.appsdeveloperblog.app.ws.exceptions;


public class AddressServiceException extends RuntimeException {

    private static final long serialVersionUID = 5835167543485021280L;

    public AddressServiceException(String message) {
        super(message);
    }
}
