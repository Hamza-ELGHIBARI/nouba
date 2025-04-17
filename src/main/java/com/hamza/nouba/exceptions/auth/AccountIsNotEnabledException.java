package com.hamza.nouba.exceptions.auth;

public class AccountIsNotEnabledException extends RuntimeException {
    public AccountIsNotEnabledException(String message) {
        super(message);
    }
}
