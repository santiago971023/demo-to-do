package com.todoapp.demo.infraestructure.exceptionHandler;

import com.todoapp.demo.application.exception.ErrorMessagesApplication;
import com.todoapp.demo.application.exception.UserValidationException;
import com.todoapp.demo.domain.exception.ErrorMessagesDomain;
import com.todoapp.demo.domain.exception.TaskValidationExceptionDomain;
import com.todoapp.demo.infraestructure.exception.*;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import java.util.Collections;
import java.util.Map;

@ControllerAdvice
public class ControllerAdvisor {

    private static final String MESSAGE ="Message";

    @ExceptionHandler (UserAlreadyExistsException.class)
    public ResponseEntity<Map<String, String>> handleUserAlreadyExistsException(UserAlreadyExistsException userAlreadyExistsException){

        return ResponseEntity.status(HttpStatus.CONFLICT)
                .body(Collections.singletonMap(MESSAGE, ExceptionResponse.USER_ALREADY_EXISTS.getMessage()));

    }

    @ExceptionHandler (UserNotFoundException.class)
    public ResponseEntity<Map<String, String>> handleNotDataFoundException(UserNotFoundException userNotFoundException){

        return ResponseEntity.status(HttpStatus.NOT_FOUND)
                .body(Collections.singletonMap(MESSAGE, ExceptionResponse.USER_NOT_FOUND.getMessage()));

    }

    @ExceptionHandler (TaskAlreadyExistsException.class)
    public ResponseEntity<Map<String, String>> handleTaskAlreadyExistsException(TaskAlreadyExistsException taskAlreadyExist){

        return ResponseEntity.status(HttpStatus.CONFLICT)
                .body(Collections.singletonMap(MESSAGE, ExceptionResponse.TASK_ALREADY_EXISTS.getMessage()));

    }

    @ExceptionHandler (TaskNotFoundException.class)
    public ResponseEntity<Map<String, String>> handleTaskNotDataFoundException(TaskNotFoundException taskNotFoundException){

        return ResponseEntity.status(HttpStatus.NOT_FOUND)
                .body(Collections.singletonMap(MESSAGE, ExceptionResponse.TASK_NOT_FOUND.getMessage()));

    }

    @ExceptionHandler(UserValidationException.class)
    public ResponseEntity<Map<String, String>> userCantCreateAnotherUser(UserValidationException userValidationException){
        return ResponseEntity.status(HttpStatus.CONFLICT)
                .body(Collections.singletonMap(MESSAGE, ErrorMessagesApplication.CANT_CREATE.getMessage()));
    }

    @ExceptionHandler(TaskValidationExceptionDomain.class)
    public ResponseEntity<Map<String, String>> finishDateCannotBeBeforeThatStartDate(TaskValidationExceptionDomain taskValidationExceptionDomain){
        return ResponseEntity.status(HttpStatus.CONFLICT)
                .body(Collections.singletonMap(MESSAGE, ErrorMessagesDomain.FINISHDATE_INVALID.getMessage()));
    }
}
