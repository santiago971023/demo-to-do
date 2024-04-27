package com.todoapp.demo.application.exception.user;

public class CantCreateAnotherUserValidationException extends UserValidationException{
    public CantCreateAnotherUserValidationException(String message) {
        super(message);
    }
}
