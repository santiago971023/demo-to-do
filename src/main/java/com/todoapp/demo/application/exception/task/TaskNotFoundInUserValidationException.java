package com.todoapp.demo.application.exception.task;

import com.todoapp.demo.application.exception.user.UserValidationException;

public class TaskNotFoundInUserValidationException extends TaskValidationException {
    public TaskNotFoundInUserValidationException(String message) {
        super(message);
    }
}
