package com.todoapp.demo.infraestructure.output.jpa.adapter;

import com.todoapp.demo.domain.model.Task;
import com.todoapp.demo.domain.model.User;
import com.todoapp.demo.domain.spi.ITaskPersistencePort;
import com.todoapp.demo.infraestructure.exception.NoDataFoundException;
import com.todoapp.demo.infraestructure.exception.TaskAlreadyExistsException;
import com.todoapp.demo.infraestructure.exception.TaskNotFoundException;
import com.todoapp.demo.infraestructure.exception.UserNotFoundException;
import com.todoapp.demo.infraestructure.output.jpa.entities.TaskEntity;
import com.todoapp.demo.infraestructure.output.jpa.mapper.ITaskEntityMapper;
import com.todoapp.demo.infraestructure.output.jpa.mapper.IUserEntityMapper;
import com.todoapp.demo.infraestructure.output.jpa.repository.ITaskRepository;
import com.todoapp.demo.infraestructure.output.jpa.repository.IUserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.util.List;

@RequiredArgsConstructor
public class TaskJpaAdapter implements ITaskPersistencePort {

    private final ITaskRepository taskRepository;
    private final ITaskEntityMapper taskEntityMapper;
    private final IUserRepository userRepository;
    private final IUserEntityMapper userEntityMapper;


    @Override
    public void createTask(Task task) {
        if (taskRepository.findById(task.getId()).isPresent()){
            throw new TaskAlreadyExistsException();
        }
        taskRepository.save(taskEntityMapper.toEntity(task));
    }

    @Override
    public void updateTask(Task task) {
        if(taskRepository.findById(task.getId()).isEmpty()){
            throw new TaskNotFoundException();
        }
        taskRepository.save(taskEntityMapper.toEntity(task));
    }

    @Override
    public void deleteTask(Long taskId) {
        taskRepository.deleteById(taskId);
    }

    @Override
    public Task getTaskById(Long taskId) {
        Task task = taskEntityMapper.toTask(taskRepository.findById(taskId).orElseThrow(TaskNotFoundException::new));

        List<String> userIdsOfTask = taskRepository.getUserIdsForTask(taskId);

        task.setIdUsers(userIdsOfTask);

        return task;

        //return taskEntityMapper.toTask(taskRepository.findById(taskId).orElseThrow(TaskNotFoundException::new));
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
}
