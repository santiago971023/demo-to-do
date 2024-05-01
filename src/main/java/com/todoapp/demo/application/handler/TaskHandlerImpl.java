package com.todoapp.demo.application.handler;

import com.todoapp.demo.application.dto.request.TaskRequestDto;
import com.todoapp.demo.application.dto.response.TaskResponseDto;
import com.todoapp.demo.application.exception.ErrorMessagesApplication;
import com.todoapp.demo.application.exception.task.CantCreateTaskValidationException;
import com.todoapp.demo.application.exception.task.CantDeleteTaskValidationException;
import com.todoapp.demo.application.exception.task.CantUpdateTaskValidationException;
import com.todoapp.demo.application.mapper.ITaskRequestMapper;
import com.todoapp.demo.application.mapper.ITaskResponseMapper;
import com.todoapp.demo.domain.Role;
import com.todoapp.demo.domain.Status;
import com.todoapp.demo.domain.api.ITaskServicePort;
import com.todoapp.demo.domain.api.IUserServicePort;
import com.todoapp.demo.domain.api.IUserTaskServicePort;
import com.todoapp.demo.domain.exception.task.TaskValidationExceptionDomain;
import com.todoapp.demo.domain.model.Task;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;


@Service
@RequiredArgsConstructor
@Transactional
public class TaskHandlerImpl implements ITaskHandler{

    private final ITaskServicePort taskServicePort;
    private final ITaskRequestMapper taskRequestMapper;
    private final ITaskResponseMapper taskResponseMapper;
    private final IUserServicePort userServicePort;

    private final IUserTaskServicePort userTaskServicePort;



    @Override
    public void createTask(TaskRequestDto taskRequestDtoToCreate, String idUserCreator) {
        if(!canCreateTask(taskRequestDtoToCreate, idUserCreator)){
            throw new CantCreateTaskValidationException(ErrorMessagesApplication.CANT_CREATE_TASK.getMessage());
        }
        Task taskToCreate = taskRequestMapper.toTask(taskRequestDtoToCreate);
        taskServicePort.createTask(taskToCreate);
    }

    @Override
    public void updateTask(TaskRequestDto taskRequestDtoToUpdate, String idUserUpdater) {
        if(!canUpdateTask(taskRequestDtoToUpdate, idUserUpdater)){
            throw new CantUpdateTaskValidationException(ErrorMessagesApplication.CANT_UPDATE_TASK.getMessage());
        }
        Task taskToUpdate = taskRequestMapper.toTask(taskRequestDtoToUpdate);
        taskToUpdate.setStartDate(taskServicePort.getTaskById(taskRequestDtoToUpdate.getId()).getStartDate());

        taskServicePort.updateTask(taskToUpdate);
    }
    @Override
    public void deleteTask(Long idTaskToDelete, String idDeleter) {
        if(!canDeleteTask(idTaskToDelete, idDeleter)){
            throw new CantDeleteTaskValidationException(ErrorMessagesApplication.CANT_DELETE_TASK.getMessage());
        }
        taskServicePort.deleteTask(idTaskToDelete);
    }
    @Override
    public void updateStatusTask(String status, String idUpdater, Long idTaskUpdate) {
        if (!canUpdateStatusTask(status, idUpdater, idTaskUpdate)){
            throw new CantUpdateTaskValidationException(ErrorMessagesApplication.CANT_UPDATE_STATUS_TASK.getMessage());
        }
        //
        Task task = taskServicePort.getTaskById(idTaskUpdate);

        if(Arrays.stream(Status.values()).anyMatch(s -> s.toString().equalsIgnoreCase(status))){
            task.setStatus(Status.valueOf(status.toUpperCase()));
            System.out.println("Se identifica correctamente el status");
        }
        taskServicePort.updateTaskStatus(idTaskUpdate, status);
        taskServicePort.updateTask(task);
    }

    @Override
    public List<TaskResponseDto> getTaskByStartDate(LocalDate date) {
        List<Task> taskList = taskServicePort.getTaskByStartDate(date);
        return taskResponseMapper.toTaskResponseList(taskList);
    }

    @Override
    public List<TaskResponseDto> getTaskByFinishDate(LocalDate date) {
        List<Task> taskList = taskServicePort.getTaskByFinishDate(date);
        return taskResponseMapper.toTaskResponseList(taskList);
    }

    @Override
    public TaskResponseDto getTaskById(Long idTask) {
        Task task = taskServicePort.getTaskById(idTask);
        return taskResponseMapper.toTaskResponse(task);
    }

    @Override
    public List<TaskResponseDto> getAllTasks() {
        List<Task> taskList = taskServicePort.getAllTasks();
        return taskResponseMapper.toTaskResponseList(taskList);
    }

    @Override
    public List<TaskResponseDto> getTaskByIdUser(String idUser) {
        List<Task> taskList = taskServicePort.getTasksByUser(idUser);
        return taskResponseMapper.toTaskResponseList(taskList);
    }

    @Override
    public List<TaskResponseDto> getTaskByStatus(String status) {

        List<Task> allTasks = taskServicePort.getAllTasks();
        List<Task> tasksWithStatus = new ArrayList<>();
        for (Task task : allTasks) {
            if(task.getStatus().toString().equalsIgnoreCase(status)){
                tasksWithStatus.add(task);
            }
        }
        return taskResponseMapper.toTaskResponseList(tasksWithStatus);
    }

    @Override
    public void removeUserTask(Long idTask, String idUserToDelete) {

        if (!existUserOnTask(idUserToDelete, idTask)){
            throw new TaskValidationExceptionDomain(ErrorMessagesApplication.ID_USER_NOTFOUND_IN_TASK.getMessage());
        }
        taskServicePort.getTaskById(idTask).getIdUsers().remove(idUserToDelete);
    }

    @Override
    public void assingUserTask(Long idTask, String idUserToAssing) {
        if (existUserOnTask(idUserToAssing, idTask)){
            throw new TaskValidationExceptionDomain(ErrorMessagesApplication.ID_USER_ALREADY_EXIST_ON_TASK.getMessage());
        }
        taskServicePort.getTaskById(idTask).getIdUsers().add(idUserToAssing);
    }

    @Override
   public List<TaskResponseDto> getTasksByMonth(Integer numberMonth){
        List<Task> allTasks = taskServicePort.getAllTasks();
        List<TaskResponseDto> taskResponseDtoList = taskResponseMapper.toTaskResponseList(allTasks);
        return taskResponseDtoList.stream()
                .filter(task -> task.getStartDate().getMonth().getValue() == numberMonth)
                .collect(Collectors.toList());
    }


    // VALIDACIONES

    public boolean canCreateTask(TaskRequestDto taskToCreate, String idUser ){

        if (taskToCreate != null && userServicePort.getUserById(idUser).getRole() == Role.LEADER){
            return true;
        }
        return false;
    }
    public boolean canUpdateTask(TaskRequestDto taskToCreate, String idUser ){

        if (taskToCreate != null && userServicePort.getUserById(idUser).getRole() == Role.LEADER){
            return true;
        }
        return false;
    }

    public boolean canDeleteTask(Long idTaskToDelete, String idDeleter){
        Task taskToDelete = taskServicePort.getTaskById(idTaskToDelete);
        if (taskToDelete != null && userServicePort.getUserById(idDeleter).getRole() == Role.LEADER){
            return true;
        }
        return false;
    }

    public boolean canUpdateStatusTask(String status, String idUpdater, Long idTaskUpdate){
        Task task = taskServicePort.getTaskById(idTaskUpdate);
        System.out.println("task: "+ task.toString());
        if (task != null && status.equalsIgnoreCase(Status.COMPLETED.toString()) && userServicePort.getUserById(idUpdater).getRole() == Role.LEADER ){
            System.out.println("Admin updating status to COMPLETED");
            return true;
        }
        if(task != null && (status.equalsIgnoreCase(Status.IN_PROGRESS.toString()) || status.equalsIgnoreCase(Status.REVISION.toString())) && task.getIdUsers().contains(idUpdater)){
            System.out.println("User updating status to IN_PROGRESS or REVISION");
            return true;
        }
        System.out.println("Cannot update status");
        return  false;
    }

    public boolean existUserOnTask(String idUser, Long idTask){
        Task task = taskServicePort.getTaskById(idTask);
        if(task.getIdUsers().contains(idUser)){
            return true;
        }
        return false;
    }
}
