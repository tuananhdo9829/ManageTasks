package com.tuananhdo.exception;

public class RoleNotFoundException extends Throwable {

    private final String message;


    public RoleNotFoundException(String message) {
        this.message = message;
    }

    @Override
    public String getMessage() {
        return message;
    }
}
