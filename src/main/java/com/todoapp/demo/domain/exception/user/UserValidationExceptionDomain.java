package com.todoapp.demo.domain.exception.user;

public class UserValidationExceptionDomain extends RuntimeException{

    public UserValidationExceptionDomain(String message){
        super(message);
    }

}
