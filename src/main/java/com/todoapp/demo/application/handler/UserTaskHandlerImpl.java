package com.todoapp.demo.application.handler;

import com.todoapp.demo.application.exception.ErrorMessagesApplication;
import com.todoapp.demo.application.exception.task.TaskNotFoundInUserValidationException;
import com.todoapp.demo.application.exception.user.*;
import com.todoapp.demo.domain.Role;
import com.todoapp.demo.domain.api.ITaskServicePort;
import com.todoapp.demo.domain.api.IUserServicePort;
import com.todoapp.demo.domain.api.IUserTaskServicePort;
import com.todoapp.demo.domain.model.Task;
import com.todoapp.demo.domain.model.User;
import com.todoapp.demo.infraestructure.exception.TaskNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
@Transactional
public class UserTaskHandlerImpl implements IUserTaskHandler{

    private final IUserTaskServicePort userTaskServicePort;
    private final IUserServicePort userServicePort;

    private final ITaskServicePort taskServicePort;

    @Override
    public void removeTask(String userToRemoveId, Long idTaskToRemove, String updaterId) {
        User updater = userServicePort.getUserById(updaterId);
        User userToUpdate = userServicePort.getUserById(userToRemoveId);
        if (!canUpdateTasksToUser(updaterId, userToRemoveId)) {

            throw new CantRemoveTaskFromUserValidationException(ErrorMessagesApplication.CANT_REMOVE_TASK.getMessage());

        }
        boolean isDeleted = false;
        if(userToUpdate.getTasks() != null){
            isDeleted = userToUpdate.getTasks().removeIf( id -> id.equals(idTaskToRemove));

        }
        if(!isDeleted){

            throw new TaskNotFoundInUserValidationException(ErrorMessagesApplication.TASK_NOT_FOUND_IN_USER.getMessage());
        }else{

            userTaskServicePort.removeUser(idTaskToRemove, userToRemoveId);
        }
    }

   @Override
   public void assignTask(String userToAssignId, Long idTaskToAssign, String updaterId) {
       // Obtener los usuarios y la tarea
       User userToAssign = userServicePort.getUserById(userToAssignId);
       User updater = userServicePort.getUserById(updaterId);
       Task task = taskServicePort.getTaskById(idTaskToAssign);

       // Verificar si la tarea existe
       if (task == null) {
           throw new TaskNotFoundException();
       }

       // Verificar si el usuario que realiza la actualización puede asignar tareas al usuario
       if (!canUpdateTasksToUser(updaterId, userToAssignId)) {
           throw new CantAssignTaskToUserValidationException(ErrorMessagesApplication.CANT_ASSIGN_TASK.getMessage());
       }

       // Verificar si la tarea ya está asignada al usuario


       // Asignar la tarea al usuario
       userTaskServicePort.assignUser(idTaskToAssign, userToAssignId);
   }



    @Override
    public List<String> getUsersIdsByTask(Long idTask) {
        return userTaskServicePort.getUsersByTaskId(idTask);
    }

    @Override
    public List<Long> getTaskIdsByUser(String idUser) {
        return userTaskServicePort.getTasksByUserId(idUser);
    }


    // VALIDACIÓN
    public boolean canUpdateTasksToUser(String updaterId, String userToUpdateId) {
        User updater = userServicePort.getUserById(updaterId);
        User userToUpdate = userServicePort.getUserById(userToUpdateId);
        // Si es un lider, debería poder editar unicamente la lista de tareas del usuario a ser modificado (Y este usuario debería ser un colaborador)
        if (updater != null && updater.getRole() == Role.LEADER) {
            // Aquí verificamos si quien va a ser editado, es un colaborador
            if (userToUpdate != null && userToUpdate.getRole() == Role.COLLABORATOR) {
                return true;
            }
        }
        return false;
    }
}
