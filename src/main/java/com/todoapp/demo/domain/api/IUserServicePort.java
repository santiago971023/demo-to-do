package com.todoapp.demo.domain.api;

import com.todoapp.demo.domain.model.User;

import java.util.List;

public interface IUserServicePort {

    void createUser(User newUser);

    void updateUser(User userToUpdate);

    void deleteUser(String id);

    void removeTask(String idUser, Long idTask);

    void assignTask(String idUser,Long idTask);

    List<User> getUsersByName(String name);

    List<User> getUsersByLastname(String lastname);

    List<User> getAllUsers();

    List<User> getUsersByTaskId(Long idTask);

    User getUserById(String id);

    List<User> getUsersByRole(String role);



}
