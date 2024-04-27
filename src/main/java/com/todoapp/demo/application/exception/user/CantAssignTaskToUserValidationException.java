package com.todoapp.demo.application.exception.user;

public class CantAssignTaskToUserValidationException extends UserValidationException{
    public CantAssignTaskToUserValidationException(String message) {
        super(message);
    }
}
