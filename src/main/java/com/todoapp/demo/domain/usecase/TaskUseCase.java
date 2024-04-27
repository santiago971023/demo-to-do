package com.todoapp.demo.domain.usecase;

import com.todoapp.demo.domain.Status;
import com.todoapp.demo.domain.api.ITaskServicePort;
import com.todoapp.demo.domain.exception.ErrorMessagesDomain;
import com.todoapp.demo.domain.exception.user.IdValidationExceptionDomain;
import com.todoapp.demo.domain.model.Task;
import com.todoapp.demo.domain.exception.task.*;
import com.todoapp.demo.domain.spi.ITaskPersistencePort;

import java.time.LocalDate;
import java.util.List;
import java.util.regex.Pattern;

public class TaskUseCase implements ITaskServicePort {

    private final ITaskPersistencePort taskPersistencePort;

    public TaskUseCase(ITaskPersistencePort taskPersistencePort) {
        this.taskPersistencePort = taskPersistencePort;
    }

    @Override
    public void createTask(Task task) {

        if (!isValidTitle(task.getTitle())){
            throw new TitleValidationExceptionDomain(ErrorMessagesDomain.TITLE_INVALID.getMessage());
        }
        if(!isValidDescription(task.getDescription())){

            throw new DescriptionValidationExceptionDomain(ErrorMessagesDomain.DESCRIPTION_INVALID.getMessage());
        }
        if(!isValidFinishDate(task.getFinishDate())){
            throw new FinishDateValidationExceptionDomain(ErrorMessagesDomain.FINISHDATE_INVALID.getMessage());
        }

        if (!isValidHistoryPoints(task.getHistoryPoints())){
            throw new HistoryPointsValidationExceptionDomain(ErrorMessagesDomain.HISTORYPOINTS_NULL.getMessage());
        }

        if(!isValidStatus(task.getStatus().name())){
            throw new StatusValidationExceptionDomain(ErrorMessagesDomain.STATUS_INVALID.getMessage());
        }
        taskPersistencePort.createTask(task);
    }

    @Override
    public void updateTask(Task task) {
        if (!isValidTitle(task.getTitle())){
            throw new TitleValidationExceptionDomain(ErrorMessagesDomain.TITLE_INVALID.getMessage());
        }
        if(!isValidDescription(task.getDescription())){
            throw new DescriptionValidationExceptionDomain(ErrorMessagesDomain.DESCRIPTION_INVALID.getMessage());
        }
        if(!isValidFinishDate(task.getFinishDate())){
            throw new FinishDateValidationExceptionDomain(ErrorMessagesDomain.FINISHDATE_INVALID.getMessage());
        }
        if(!isValidStatus(task.getStatus().name())){
            throw new StatusValidationExceptionDomain((ErrorMessagesDomain.STATUS_INVALID.getMessage()));
        }
        if (!isValidHistoryPoints(task.getHistoryPoints())){
            throw new HistoryPointsValidationExceptionDomain(ErrorMessagesDomain.HISTORYPOINTS_NULL.getMessage());
        }
        taskPersistencePort.updateTask(task);

    }

    @Override
    public void deleteTask(Long taskId) {
        taskPersistencePort.deleteTask(taskId);

    }

    @Override
    public Task getTaskById(Long taskId) {
        return taskPersistencePort.getTaskById(taskId);
    }

    @Override
    public List<Task> getAllTasks() {
        return taskPersistencePort.getAllTasks();
    }

    @Override
    public List<Task> getTasksByUser(String userId) {
        return taskPersistencePort.getTasksByUser(userId);
    }

    @Override
    public List<Task> getTaskByStatus(String status) {
        return taskPersistencePort.getTaskByStatus(status);
    }

    @Override
    public List<Task> getTaskByStartDate(LocalDate date) {
        return taskPersistencePort.getTaskByStartDate(date);
    }

    @Override
    public List<Task> getTaskByFinishDate(LocalDate date) {
        return taskPersistencePort.getTaskByFinishDate(date);
    }

    @Override
    public void updateTaskStatus(Long taskId,String status) {
        if (!isValidIdTask(taskId)){
            throw new IdValidationExceptionDomain(ErrorMessagesDomain.IDTASK_INVALID.getMessage());
        }
        if (!isValidStatus(status)){
            throw new StatusValidationExceptionDomain(ErrorMessagesDomain.STATUS_INVALID.getMessage());
        }
        taskPersistencePort.updateTaskStatus(taskId, status);

    }


    //VALIDACIONES
    public boolean isValidIdTask(Long idTask){
        if(idTask == null || idTask.toString().isEmpty() ){
            return false;
        }
        return true;
    }

    public boolean isValidTitle(String title){
        if (title == null || title.isEmpty()){
            return false;
        }
        return true;
    }

    public boolean isValidDescription(String description){
        if (description == null || description.isEmpty()){
            return false;
        }
        String regex = ".{15,}";
        return description.matches(regex);
    }

    public boolean isValidFinishDate(LocalDate finishDate) {

        if (finishDate == null || finishDate.toString().isEmpty()) {
            return false;
        }
        if (finishDate.isBefore(LocalDate.now())) {
            return false;
        }
        return true;
    }

    public boolean isValidStatus(String status) {
        try {
            Status[] validStatus = Status.values();
            for (Status valStatus : validStatus) {
                if (valStatus.name().equalsIgnoreCase(status)) {
                    return true; //El valor coincide con un status válido
                }
            }
            return false;
        } catch (IllegalArgumentException e) {
            //Capturar excepción si el valor no coincide con ningún enum válido
            return false;
        }
    }

    public boolean isValidHistoryPoints(Integer historyPoints) {
        // Verificar si el número es nulo
        if (historyPoints == null){
            return false;
        }
        String numberString = String.valueOf(historyPoints);
        String regex = "[0-9]+"; // La expresión regular solo permite caracteres del 0 al 9 sin caracteres especiales
        return Pattern.matches(regex, numberString);
    }

    public boolean isValidIdUser(String idUser) {
        if (idUser == null || idUser.isEmpty()) {
            return false;
        }
        // Usamos una expresión regular para verificar que el número contenga solo números y tenga máximo 10 dígitos
        String regex = "\\d{1,10}";
        return idUser.matches(regex);
    }
}
