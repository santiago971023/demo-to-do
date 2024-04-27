package com.todoapp.demo.application.exception.user;

public class CantDeleteAnotherUSerValidationException extends UserValidationException{
    public CantDeleteAnotherUSerValidationException(String message) {
        super(message);
    }
}
