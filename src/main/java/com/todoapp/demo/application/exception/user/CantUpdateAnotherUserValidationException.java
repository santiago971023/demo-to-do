package com.todoapp.demo.application.exception.user;

public class CantUpdateAnotherUserValidationException extends UserValidationException{
    public CantUpdateAnotherUserValidationException(String message) {
        super(message);
    }
}
