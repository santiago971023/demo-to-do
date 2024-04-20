package com.todoapp.demo.infraestructure.input.rest;


import com.todoapp.demo.application.dto.request.UserRequestDto;
import com.todoapp.demo.application.dto.response.UserResponseDto;
import com.todoapp.demo.application.exception.ErrorMessagesApplication;
import com.todoapp.demo.application.exception.UserValidationException;
import com.todoapp.demo.application.handler.ITaskHandler;
import com.todoapp.demo.application.handler.IUserHandler;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;

@RestController
@RequestMapping("/users")
@RequiredArgsConstructor
public class UserController {

    private final IUserHandler userHandler;
    private final ITaskHandler taskHandler;

    //User
    @PostMapping("/save/{idCreator}")
    public ResponseEntity<Void> saveUser(@RequestBody UserRequestDto userRequestDtoToCreate, @PathVariable String idCreator) {
//        try{
//            userHandler.createUser(userRequestDtoToCreate, idCreator);
//            return ResponseEntity.status(HttpStatus.CREATED).build();
//        }catch(UserValidationException e){
//            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, ErrorMessagesApplication.CANT_CREATE.getMessage(), e);
//        }
                 userHandler.createUser(userRequestDtoToCreate, idCreator);
           return ResponseEntity.status(HttpStatus.CREATED).build();
    }

    @PutMapping("/update/{idUpdater}")
    public ResponseEntity<Void> updateUser(@RequestBody UserRequestDto userRequestDto, @PathVariable String idUpdater){
        userHandler.updateUser(userRequestDto, idUpdater);
        return ResponseEntity.status(HttpStatus.ACCEPTED).build();

    }

    @GetMapping("/all")
    public ResponseEntity<List<UserResponseDto>> getAllUsers() {
        return ResponseEntity.ok(userHandler.getAllUsers());
    }

    @GetMapping("/id/{id}")
    public ResponseEntity<UserResponseDto> getUsersById(@PathVariable String id) {
        return ResponseEntity.ok(userHandler.getUserById(id));
    }


    @GetMapping("/name/{name}")
    public ResponseEntity<List<UserResponseDto>> getUsersByName(@PathVariable String name) {
        return ResponseEntity.ok(userHandler.getUsersByName(name));
    }

    @GetMapping("/lastname/{lastname}")
    public ResponseEntity<List<UserResponseDto>> getUsersByLastName(@PathVariable String lastname) {
        return ResponseEntity.ok(userHandler.getUsersByLastname(lastname));
    }

    @GetMapping("/task-id/{idTask}")
    public ResponseEntity<List<UserResponseDto>> getUsersByTaskId(@PathVariable Long idTask){
        return ResponseEntity.ok(userHandler.getUsersByTaskId(idTask));
    }

    @GetMapping("/role/{role}")
    public ResponseEntity<List<UserResponseDto>> getUserByRole(@PathVariable String role){
        return ResponseEntity.ok(userHandler.getUsersByRole(role));
    }


    @DeleteMapping("/delete/{idUserToDelete}/{idUserDeleter}")
    public ResponseEntity<Void> deleteById(@PathVariable String idUserToDelete, @PathVariable String idUserDeleter ){
        userHandler.deleteUser(idUserToDelete, idUserDeleter);
        return ResponseEntity.status(HttpStatus.ACCEPTED).build();


    }

//    @PutMapping("/remove-task/{idUserToDelete}/{idTaskToDelete}/{idUserDeleter}")
//    public ResponseEntity<Void> removeTaskFromUser(@PathVariable String idUserToDelete, @PathVariable String idUserDeleter, @PathVariable Long idTaskToDelete){
//        userHandler.removeTask(idUserToDelete, idTaskToDelete, idUserDeleter);
//        taskHandler.removeUserTask(idTaskToDelete, idUserToDelete);
//
//        return ResponseEntity.status(HttpStatus.ACCEPTED).build();
//
//    }
//
//    @PutMapping("/assign-task/{idUserToAssign}/{idTaskToAssign}/{idUserAssigner}")
//    public ResponseEntity<Void> assignTaskToUser(@PathVariable String idUserToAssign, @PathVariable String idUserAssigner, @PathVariable Long idTaskToAssign){
//        userHandler.assignTask(idUserToAssign, idTaskToAssign, idUserAssigner);
//        taskHandler.assingUserTask(idTaskToAssign,idUserToAssign);
//
//        return ResponseEntity.status(HttpStatus.ACCEPTED).build();
//    }



}