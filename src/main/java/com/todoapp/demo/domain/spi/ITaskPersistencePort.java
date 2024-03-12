package com.todoapp.demo.domain.spi;

import com.todoapp.demo.domain.model.Task;
import com.todoapp.demo.domain.model.User;

import java.time.LocalDate;
import java.util.List;

public interface ITaskPersistencePort {

    void createTask(Task task, User userCreator, User userToAssign);

    void updateTask(Task task, User updaterUser);

    void deleteTask(Long taskId, User deleterUser);

    Task getTaskById(Long taskId);

    List<Task> getAllTasks();

    List<Task> getTasksByUser(String userId);

    List<Task> getTaskByStatus(String status);

    List<Task> getTaskByStartDate(LocalDate date);

    List<Task> getTaskByFinishDate(LocalDate date);

    void updateTaskStatus(Long taskId, String userId, String status);

}
