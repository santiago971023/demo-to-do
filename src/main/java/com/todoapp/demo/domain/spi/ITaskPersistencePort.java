package com.todoapp.demo.domain.spi;

import com.todoapp.demo.domain.model.Task;

import java.time.LocalDate;
import java.util.List;

public interface ITaskPersistencePort {

    void createTask(Task task);

    void updateTask(Task task);

    void deleteTask(Long taskId);

    Task getTaskById(Long taskId);

    List<Task> getAllTasks();

    List<Task> getTasksByUser(String userId);

    List<Task> getTaskByStatus(String status);

    List<Task> getTaskByStartDate(LocalDate date);

    List<Task> getTaskByFinishDate(LocalDate date);

    void updateTaskStatus(Long taskId,String status);

    void removeUser(Long taskId, String userId);

    void assignUser(Long taskId, String userId);

    List<Task> getTasksByMonth(Integer numberMonth);

}
