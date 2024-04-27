package com.todoapp.demo.application.exception.user;

public class CantRemoveTaskFromUserValidationException extends UserValidationException{
    public CantRemoveTaskFromUserValidationException(String message) {
        super(message);
    }
}
