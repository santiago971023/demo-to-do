package com.todoapp.demo.domain.spi;

import com.todoapp.demo.domain.model.User;

import java.util.List;

public interface IUserPersistencePort {

    void createUser(User newUser);

    void updateUser(User userToUpdate);

    void deleteUser(String id);

    List<User> getUsersByName(String name);

    List<User> getUsersByLastName(String lastName);

    List<User> getUsersByTaskId(Long taskId);

    List<User> getAllUsers();

    User getUserById(String id);

    List<User> getUsersByRole(String role);

}
