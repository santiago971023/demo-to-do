package com.todoapp.demo.application.handler;

import com.todoapp.demo.application.dto.request.UserRequestDto;
import com.todoapp.demo.application.dto.response.UserResponseDto;
import com.todoapp.demo.application.exception.ErrorMessagesApplication;
import com.todoapp.demo.application.exception.user.CantCreateAnotherUserValidationException;
import com.todoapp.demo.application.mapper.IUserRequestMapper;
import com.todoapp.demo.application.mapper.IUserResponseMapper;
import com.todoapp.demo.domain.Role;
import com.todoapp.demo.domain.api.IUserServicePort;
import com.todoapp.demo.domain.api.IUserTaskServicePort;
import com.todoapp.demo.domain.model.User;
import com.todoapp.demo.application.exception.user.CantDeleteAnotherUSerValidationException;
import com.todoapp.demo.application.exception.user.CantUpdateAnotherUserValidationException;
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

    private final IUserTaskServicePort userTaskServicePort;

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
        userToUpdate.setTasks(userTaskServicePort.getTasksByUserId(userRequestDtoToUpdate.getId()));
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
