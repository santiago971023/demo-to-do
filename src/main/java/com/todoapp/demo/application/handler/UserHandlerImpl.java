package com.todoapp.demo.application.handler;

import com.todoapp.demo.application.dto.request.UserRequestDto;
import com.todoapp.demo.application.dto.response.UserResponseDto;
import com.todoapp.demo.application.exception.ErrorMessagesApplication;
import com.todoapp.demo.application.exception.user.*;
import com.todoapp.demo.application.mapper.IUserRequestMapper;
import com.todoapp.demo.application.mapper.IUserResponseMapper;
import com.todoapp.demo.domain.Role;
import com.todoapp.demo.domain.api.IUserServicePort;
import com.todoapp.demo.domain.model.User;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
@Transactional
public class UserHandlerImpl implements IUserHandler {

    private final IUserServicePort userServicePort;
    private final IUserRequestMapper userRequestMapper;
    private final IUserResponseMapper userResponseMapper;

    @Override
    public void createUser(UserRequestDto userRequestDtoToCreate, String creatorId) {

        if (!canCreateUser(creatorId, userRequestDtoToCreate)) {
            throw new CantCreateAnotherUserValidationException(ErrorMessagesApplication.CANT_CREATE.getMessage());
        }
        User userToCreate = userRequestMapper.toUser(userRequestDtoToCreate);
        userServicePort.createUser(userToCreate);
    }


    @Override
    public void updateUser(UserRequestDto userRequestDtoToUpdate, String updaterId) {

        if (!canUpdateUser(updaterId, userRequestDtoToUpdate)) {
            throw new CantUpdateAnotherUserValidationException(ErrorMessagesApplication.CANT_UPDATE.getMessage());
        }
        User userToUpdate = userRequestMapper.toUser(userRequestDtoToUpdate);
        userServicePort.updateUser(userToUpdate);
    }

    @Override
    public void deleteUser(String toDeleteId, String deleterId) {
        // Debemos preguntar si el User que elimina, efectivamente tiene permiso para hacerlo
        if (!canDelete(toDeleteId, deleterId)){
            throw new CantDeleteAnotherUSerValidationException(ErrorMessagesApplication.CANT_DELETE.getMessage());
        }
        userServicePort.deleteUser(toDeleteId);
    }

    @Override
    public UserResponseDto getUserById(String id) {
        return userResponseMapper.toUserResponse(userServicePort.getUserById(id));
    }

    @Override
    public List<UserResponseDto> getUsersByName(String name) {
        return userResponseMapper.toUserResponseList(userServicePort.getUsersByName(name));
    }

    @Override
    public List<UserResponseDto> getUsersByLastname(String lastname) {
        return userResponseMapper.toUserResponseList(userServicePort.getUsersByLastname(lastname));
    }

    @Override
    public List<UserResponseDto> getAllUsers() {
        return userResponseMapper.toUserResponseList(userServicePort.getAllUsers());
    }

    @Override
    public List<UserResponseDto> getUsersByRole(String role) {

        List<User> allUsers = userServicePort.getAllUsers();
        List<User> usersWithRole = new ArrayList<>();
        for (User user : allUsers) {
            if(user.getRole().toString().equalsIgnoreCase(role)){
                usersWithRole.add(user);
            }
        }
        return userResponseMapper.toUserResponseList(usersWithRole);
    }

    @Override
    public List<UserResponseDto> getUsersByTaskId(Long idTask) {
        return userResponseMapper.toUserResponseList(userServicePort.getUsersByTaskId(idTask));
    }

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
            throw new UserValidationException(ErrorMessagesApplication.TASK_NOT_FOUND_IN_USER.getMessage());
        }else{
            userServicePort.updateUser(userToUpdate);
        }
    }


    @Override
    public void assignTask(String userToAssignId, Long idTaskToAssign, String updaterId) {

        User userToAssign = userServicePort.getUserById(userToAssignId);
        User updater = userServicePort.getUserById(updaterId);

        if (!canUpdateTasksToUser(updaterId, userToAssignId)) {
            throw new CantAssignTaskToUserValidationException(ErrorMessagesApplication.CANT_ASSIGN_TASK.getMessage());
        }
        if (userToAssign.getTasks().contains(idTaskToAssign)){
            throw new UserValidationException(ErrorMessagesApplication.TASK_ALREADY.getMessage());
        }else {
            userToAssign.getTasks().add(idTaskToAssign);
            userServicePort.updateUser(userToAssign);
        }
    }

    // VALIDACIONES

    public boolean canCreateUser( String creatorId, UserRequestDto userBeingCreated) {
        User userCreator = userServicePort.getUserById(creatorId);
        //  Debemos verificar si el usuario que crea es un admin

        if (userCreator != null && userCreator.getRole() == Role.ADMIN) {
            // Si quien está siendo creado es un Empleado o un Lider
            if (userBeingCreated != null &&
                    (userBeingCreated.getRole() == Role.COLLABORATOR || userBeingCreated.getRole() == Role.LEADER)) {

                return true; // El usuario creador es admin y puede crear usuarios con roles de colaborador y lider
            }
        }
        return false; // El usuario creador no tiene permisos adecuados
    }

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

    public boolean canUpdateUser(String updaterId, UserRequestDto userToUpdate) {
        User updater = userServicePort.getUserById(updaterId);
        //  Debemos verificar si el usuario que actualiza es un admin
        if (updater != null && updater.getRole() == Role.ADMIN) {
            // Si quien está siendo actualizado es un Empleado o un Lider
            if (userToUpdate != null &&
                    (userToUpdate.getRole() == Role.COLLABORATOR || userToUpdate.getRole() == Role.LEADER)) {
                return true; // Quien actualiza es admin y tiene permisos para actualizar Colaborador y Lideres
            }
        }
        return false; // El usuario actualizador no tiene permisos de Admin
    }


    public boolean canDelete(String userToDeleteId, String deleterUserId) {
        User toDelete = userServicePort.getUserById(userToDeleteId);
        User deleterUser = userServicePort.getUserById(deleterUserId);

        if (deleterUser != null && deleterUser.getRole() == Role.ADMIN) {
            if (toDelete != null && (toDelete.getRole() == Role.COLLABORATOR || toDelete.getRole() == Role.LEADER)) {
                return true;
            }
        }
        return false;
    }
}
