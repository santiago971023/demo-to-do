package com.todoapp.demo.domain.exception;

public class UserValidationExceptionDomain extends RuntimeException{

    public UserValidationExceptionDomain(String message){
        super(message);
    }

}
