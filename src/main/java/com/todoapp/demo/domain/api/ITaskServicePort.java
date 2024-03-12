package com.todoapp.demo.domain.api;

import com.todoapp.demo.domain.model.Task;
import com.todoapp.demo.domain.model.User;

import java.time.LocalDate;
import java.util.List;

public interface ITaskServicePort {

    void createTask(Task task);

    void updateTask(Task task);

    void deleteTask(Long taskId);

    Task getTaskById(Long taskId);

    List<Task> getAllTasks();

    List<Task> getTasksByUser(String userId);

    List<Task> getTaskByStatus(String status);

    List<Task> getTaskByStartDate(LocalDate date);

    List<Task> getTaskByFinishDate(LocalDate date);

    void updateTaskStatus(Long taskId, String userId, String status);

}
