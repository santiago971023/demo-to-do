package com.todoapp.demo.application.handler;

import com.todoapp.demo.application.dto.request.TaskRequestDto;
import com.todoapp.demo.application.dto.response.TaskResponseDto;
import com.todoapp.demo.application.exception.ErrorMessagesApplication;
import com.todoapp.demo.application.mapper.ITaskRequestMapper;
import com.todoapp.demo.application.mapper.ITaskResponseMapper;
import com.todoapp.demo.domain.Role;
import com.todoapp.demo.domain.Status;
import com.todoapp.demo.domain.api.ITaskServicePort;
import com.todoapp.demo.domain.api.IUserServicePort;
import com.todoapp.demo.domain.exception.TaskValidationException;
import com.todoapp.demo.domain.model.Task;
import com.todoapp.demo.domain.model.User;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;


@Service
@RequiredArgsConstructor
@Transactional
public class TaskHandlerImpl implements ITaskHandler{

    private final ITaskServicePort taskServicePort;
    private final ITaskRequestMapper taskRequestMapper;
    private final ITaskResponseMapper taskResponseMapper;
    private final IUserServicePort userServicePort;



    @Override
    public void createTask(TaskRequestDto taskRequestDtoToCreate, String idUserCreator) {
        if(!canCreateTask(taskRequestDtoToCreate, idUserCreator)){
            throw new TaskValidationException(ErrorMessagesApplication.CANT_CREATE_TASK.getMessage());
        }
        Task taskToCreate = taskRequestMapper.toTask(taskRequestDtoToCreate);
        taskServicePort.createTask(taskToCreate);
    }

    @Override
    public void updateTask(TaskRequestDto taskRequestDtoToUpdate, String idUserUpdater) {
        if(!canUpdateTask(taskRequestDtoToUpdate, idUserUpdater)){
            throw new TaskValidationException(ErrorMessagesApplication.CANT_UPDATE_TASK.getMessage());
        }
        Task taskToUpdate = taskRequestMapper.toTask(taskRequestDtoToUpdate);
        taskServicePort.updateTask(taskToUpdate);

    }

    @Override
    public void deleteTask(Long idTaskToDelete, String idDeleter) {
        if(!canDeleteTask(idTaskToDelete, idDeleter)){
            throw new TaskValidationException(ErrorMessagesApplication.CANT_DELETE_TASK.getMessage());
        }
        taskServicePort.deleteTask(idTaskToDelete);
    }

    @Override
    public void updateStatusTask(String status, String idUpdater, Long idTaskUpdate) {
        if (!canUpdateStatusTask(status, idUpdater, idTaskUpdate)){
            throw new TaskValidationException(ErrorMessagesApplication.CANT_UPDATE_STATUS_TASK.getMessage());
        }
        taskServicePort.updateTaskStatus(idTaskUpdate, status);

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
            throw new TaskValidationException(ErrorMessagesApplication.ID_USER_NOTFOUND_IN_TASK.getMessage());
        }
        taskServicePort.getTaskById(idTask).getIdUsers().remove(idUserToDelete);
    }

    @Override
    public void assingUserTask(Long idTask, String idUserToAssing) {
        if (existUserOnTask(idUserToAssing, idTask)){
            throw new TaskValidationException(ErrorMessagesApplication.ID_USER_ALREADY_EXIST_ON_TASK.getMessage());
        }
        taskServicePort.getTaskById(idTask).getIdUsers().add(idUserToAssing);
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
        if (task != null && status.equals(Status.COMPLETED.name()) && userServicePort.getUserById(idUpdater).getRole() == Role.ADMIN ){
            return true;
        }

        if(task != null && (status.equals(Status.IN_PROGRESS.name()) || status.equals(Status.REVISION.name())) && task.getIdUsers().contains(idUpdater)){
            return true;
        }

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
