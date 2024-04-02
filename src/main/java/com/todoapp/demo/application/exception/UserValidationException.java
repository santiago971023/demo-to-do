package com.todoapp.demo.application.exception;

public class UserValidationException extends RuntimeException{

    public UserValidationException(String message){
        super(message);
    }

}
