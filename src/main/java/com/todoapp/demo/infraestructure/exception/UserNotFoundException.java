package com.todoapp.demo.infraestructure.exception;

import com.todoapp.demo.domain.model.User;

public class UserNotFoundException extends RuntimeException{
    public UserNotFoundException(){
        super();
    }
}
