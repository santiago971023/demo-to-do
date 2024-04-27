package com.todoapp.demo.application.handler;

import com.todoapp.demo.application.dto.request.UserRequestDto;
import com.todoapp.demo.application.dto.response.UserResponseDto;

import java.util.List;

public interface IUserHandler {

    void createUser(UserRequestDto userRequestDtoToCreate, String creatorId);

    void updateUser(UserRequestDto userRequestDtoToUpdate, String creatorId);

    void deleteUser(String toDeleteId, String deleterId);

    UserResponseDto getUserById(String id);

    List<UserResponseDto> getUsersByName(String name);

    List<UserResponseDto> getUsersByLastname(String lastname);

    List<UserResponseDto> getAllUsers();

    List<UserResponseDto> getUsersByRole(String role);

    List<UserResponseDto> getUsersByTaskId(Long idTask);

//    void removeTask(String userToRemoveId, Long idTaskToRemove, String updaterId);
//
//    void assignTask(String userToAssignId, Long idTaskToAssign, String updaterId);

}
