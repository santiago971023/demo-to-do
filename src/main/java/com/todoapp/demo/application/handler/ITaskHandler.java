package com.todoapp.demo.application.handler;

import com.todoapp.demo.application.dto.request.TaskRequestDto;
import com.todoapp.demo.application.dto.request.UserRequestDto;
import com.todoapp.demo.application.dto.response.TaskResponseDto;

import java.time.LocalDate;
import java.util.List;

public interface ITaskHandler {


    void createTask(TaskRequestDto taskRequestDtoToCreate, String idUserCreator);

    void updateTask(TaskRequestDto taskRequestDtoToCreate, String idUserUpdater);

    void deleteTask(Long idTaskToDelete, String idDeleter);

    void updateStatusTask(String status, String idUpdater, Long idTaskUpdate);

    List<TaskResponseDto> getTaskByStartDate(LocalDate date);
    List<TaskResponseDto> getTaskByFinishDate(LocalDate date);

    TaskResponseDto getTaskById(Long idTask);

    List<TaskResponseDto> getAllTasks();

    List<TaskResponseDto> getTaskByIdUser(String idUser);

    List<TaskResponseDto> getTaskByStatus(String status);

    List<TaskResponseDto> getTasksByMonth(Integer numberMonth);

    void removeUserTask(Long idTask, String idUserToDelete);
    void assingUserTask(Long idTask, String idUserToAssing);












}
