package com.trujca.mapoli.data.auth.exception;

public class UserNotLoggedInException extends Exception {

    public UserNotLoggedInException() {
        super("User is not logged in!");
    }
}
