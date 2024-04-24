package com.todoapp.demo.infraestructure.output.jpa.adapter;

import com.todoapp.demo.application.exception.ErrorMessagesApplication;
import com.todoapp.demo.application.exception.TaskValidationException;
import com.todoapp.demo.domain.exception.ErrorMessagesDomain;
import com.todoapp.demo.domain.exception.task.TaskValidationExceptionDomain;
import com.todoapp.demo.domain.model.Task;
import com.todoapp.demo.domain.spi.ITaskPersistencePort;
import com.todoapp.demo.infraestructure.exception.NoDataFoundException;
import com.todoapp.demo.infraestructure.exception.TaskAlreadyExistsException;
import com.todoapp.demo.infraestructure.exception.TaskNotFoundException;
import com.todoapp.demo.infraestructure.exceptionHandler.ExceptionResponse;
import com.todoapp.demo.infraestructure.output.jpa.entities.TaskEntity;
import com.todoapp.demo.infraestructure.output.jpa.mapper.ITaskEntityMapper;
import com.todoapp.demo.infraestructure.output.jpa.repository.ITaskRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.server.ResponseStatusException;

import java.time.LocalDate;
import java.util.List;

@RequiredArgsConstructor
public class TaskJpaAdapter implements ITaskPersistencePort {

    private final ITaskRepository taskRepository;
    private final ITaskEntityMapper taskEntityMapper;



    @Override
    public void createTask(Task task) {
        try {
            if (taskRepository.findById(task.getId()).isPresent()) {
                throw new TaskAlreadyExistsException();
            }
            taskRepository.save(taskEntityMapper.toEntity(task));

        } catch (TaskAlreadyExistsException e) {
            throw new ResponseStatusException(HttpStatus.CONFLICT, ExceptionResponse.TASK_ALREADY_EXISTS.getMessage(), e);
        }catch (TaskValidationException e) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, ErrorMessagesApplication.CANT_CREATE_TASK.getMessage(), e);
        }catch (TaskValidationExceptionDomain e){
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, ErrorMessagesDomain.FINISHDATE_INVALID.getMessage(), e);
        }catch (Exception e) {
            throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR, "Error al crear tarea", e);
        }
    }

    @Override
    public void updateTask(Task task) {
        try{
            if(taskRepository.findById(task.getId()).isEmpty()){
                throw new TaskNotFoundException();
            }
            taskRepository.save(taskEntityMapper.toEntity(task));
        }catch (TaskNotFoundException e){
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, ExceptionResponse.TASK_NOT_FOUND.getMessage(), e);
        }catch (TaskValidationException e){
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, ErrorMessagesApplication.CANT_UPDATE_TASK.getMessage(), e);
        }catch (Exception e){
            throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR, "Error al actualizar tarea", e);
        }

    }

    @Override
    public void deleteTask(Long taskId) {
        try {
            if (taskRepository.findById(taskId).isEmpty()) {
                throw new TaskNotFoundException();
            }
            taskRepository.deleteById(taskId);
        } catch (TaskNotFoundException e) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, ExceptionResponse.TASK_NOT_FOUND.getMessage(), e);
        } catch (TaskValidationException e) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, ErrorMessagesApplication.CANT_DELETE_TASK.getMessage(), e);

        } catch (Exception e) {
            throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR, "Error al eliminar tarea", e);

        }
    }

    @Override
    public Task getTaskById(Long taskId) {
        Task task = taskEntityMapper.toTask(taskRepository.findById(taskId).orElseThrow(TaskNotFoundException::new));

        List<String> userIdsOfTask = taskRepository.getUserIdsForTask(taskId);

        task.setIdUsers(userIdsOfTask);

        return task;
    }

    @Override
    public List<Task> getAllTasks() {

        List<TaskEntity> taskList = taskRepository.findAll();

        if (taskList.isEmpty()){
            throw new NoDataFoundException();
        }
        return taskEntityMapper.toTaskList(taskList);
    }

    @Override
    public List<Task> getTasksByUser(String userId) {
        return taskEntityMapper.toTaskList(taskRepository.findByUserIds(userId).orElseThrow(NoDataFoundException::new));
    }

    @Override
    public List<Task> getTaskByStatus(String status) {
        return taskEntityMapper.toTaskList(taskRepository.findByStatus(status).orElseThrow(NoDataFoundException::new));
    }

    @Override
    public List<Task> getTaskByStartDate(LocalDate startDate) {
        return taskEntityMapper.toTaskList(taskRepository.findByStartDate(startDate).orElseThrow(NoDataFoundException::new));
    }

    @Override
    public List<Task> getTaskByFinishDate(LocalDate finishDate) {
        return taskEntityMapper.toTaskList(taskRepository.findByFinishDate(finishDate).orElseThrow(NoDataFoundException::new));
    }

    @Override
    public void updateTaskStatus(Long taskId, String status) {


    }

    @Override
    public void removeUser(Long taskId, String userId) {

    }

    @Override
    @Transactional
    public void assignUser(Long taskId, String userId) {

       taskRepository.assignTaskToUser(userId, taskId);
    }
    @Override
    public List<Task> getTasksByMonth(Integer numberMonth){
        return taskEntityMapper.toTaskList(taskRepository.findTasksByMonth(numberMonth).orElseThrow(NoDataFoundException::new));
    }
}
