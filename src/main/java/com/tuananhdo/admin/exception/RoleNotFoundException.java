package com.tuananhdo.admin.exception;

public class RoleNotFoundException extends Exception {

    private final String message;


    public RoleNotFoundException(String message) {
        this.message = message;
    }

    @Override
    public String getMessage() {
        return message;
    }
}
