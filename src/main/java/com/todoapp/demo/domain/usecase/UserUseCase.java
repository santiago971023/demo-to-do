package com.todoapp.demo.domain.usecase;

import com.todoapp.demo.domain.Role;
import com.todoapp.demo.domain.api.IUserServicePort;
import com.todoapp.demo.domain.exception.ErrorMessagesDomain;
import com.todoapp.demo.domain.model.User;
import com.todoapp.demo.domain.spi.IUserPersistencePort;
import com.todoapp.demo.domain.exception.user.*;

import java.util.List;

public class UserUseCase implements IUserServicePort {


    private final IUserPersistencePort userPersistencePort;

    public UserUseCase(IUserPersistencePort userPersistencePort) {
        this.userPersistencePort = userPersistencePort;
    }


    @Override
    public void createUser(User newUser) {

        if (!isValidId(newUser.getId())) {
            throw new IdValidationExceptionDomain(ErrorMessagesDomain.ID_INVALID.getMessage());
        }
        if (!isValidName(newUser.getName())) {
            throw new NameValidationExceptionDomain(ErrorMessagesDomain.NAME_INVALID.getMessage());
        }
        if (!isValidName(newUser.getLastname())) {
            throw new LastNameValidationExceptionDomain(ErrorMessagesDomain.LASTNAME_INVALID.getMessage());
        }
        if (!isValidEmail(newUser.getEmail())) {
            throw new EmailValidationExceptionDomain(ErrorMessagesDomain.EMAIL_INVALID.getMessage());
        }
        if (!isValidPassword(newUser.getPassword())) {
            throw new PasswordValidationExceptionDomain(ErrorMessagesDomain.PASSWORD_INVALID.getMessage());
        }
        if (!isValidRole(newUser.getRole().toString())) {
            throw new RoleValidationExceptionDomain(ErrorMessagesDomain.ROLE_INVALID.getMessage());
        }
        userPersistencePort.createUser(newUser);
    }

    @Override
    public void updateUser(User userToUpdate) {
        if (!isValidId(userToUpdate.getId())) {
            throw new IdValidationExceptionDomain(ErrorMessagesDomain.ID_INVALID.getMessage());
        }
        if (!isValidName(userToUpdate.getName())) {
            throw new NameValidationExceptionDomain(ErrorMessagesDomain.NAME_INVALID.getMessage());
        }
        if (!isValidName(userToUpdate.getLastname())) {
            throw new LastNameValidationExceptionDomain(ErrorMessagesDomain.LASTNAME_INVALID.getMessage());
        }
        if (!isValidEmail(userToUpdate.getEmail())) {
            throw new EmailValidationExceptionDomain(ErrorMessagesDomain.EMAIL_INVALID.getMessage());
        }
        if (!isValidPassword(userToUpdate.getPassword())) {
            throw new PasswordValidationExceptionDomain(ErrorMessagesDomain.PASSWORD_INVALID.getMessage());
        }
        if (!isValidRole(userToUpdate.getRole().toString())) {
            throw new RoleValidationExceptionDomain(ErrorMessagesDomain.ROLE_INVALID.getMessage());
        }

        userPersistencePort.updateUser(userToUpdate);
    }

    @Override
    public void deleteUser(String id) {
        userPersistencePort.deleteUser(id);
    }

    @Override
    public List<User> getUsersByName(String name) {
        return userPersistencePort.getUsersByName(name);
    }

    @Override
    public List<User> getUsersByLastname(String lastname) {
        return userPersistencePort.getUsersByLastName(lastname);
    }

    @Override
    public List<User> getAllUsers() {return userPersistencePort.getAllUsers();
    }

    @Override
    public List<User> getUsersByTaskId(Long idTask) {
        return userPersistencePort.getUsersByTaskId(idTask);
    }

    @Override
    public User getUserById(String id) {
        return userPersistencePort.getUserById(id);
    }

    @Override
    public List<User> getUsersByRole(String role) {
        return userPersistencePort.getUsersByRole(role);
    }



    // VALIDACIONES
    public boolean isValidId(String id) {
        if (id == null || id.isEmpty()) {
            return false;
        }
        // Usamos una expresión regular para verificar que el número contenga solo números y tenga máximo 10 dígitos
        String regex = "\\d{1,10}";
        return id.matches(regex);
    }

    public boolean isValidName(String name) {
        if (name == null || name.isEmpty()) {
            return false;
        }
        // Usamos una expresión regular para verificar que el nombre contenga solo letras
        String regex = "^[A-Za-zÁÉÍÓÚáéíóúÜüÑñ ]+$";
        return name.matches(regex);
    }

    public boolean isValidRole(String role) {
        try {
            //Obtengo los roles del enum Role
            Role[] validRoles = Role.values();
            //Verifico si el valor de 'role' coincide con alguno de los valores de mi Enum
            for (Role validRole : validRoles) {
                if (validRole.name().equalsIgnoreCase(role)) {
                    return true; //El valor coincide con un rol válido
                }
            }
            // Si no se encontró coincidencia con ningún rol válido
            return false;
        } catch (IllegalArgumentException e) {
            //Capturar excepción si el valor no coincide con ningún enum válido
            return false;
        }
    }

    public boolean isValidEmail(String email) {
        if (email == null || email.isEmpty()) {
            return false;
        }
        String regex = "^[A-Za-z0-9+_.-]+@(.+)$";
        return email.matches(regex);
    }

    public boolean isValidPassword(String password) {
        if (password == null || password.isEmpty()) {
            return false;
        }
        String regex = "^(?=.*[a-z])(?=.*[A-Z])(?=.*\\d)(?=.*[@#$%^&*!])[A-Za-z\\d@#$%^&*!]{8,}$";
        return password.matches(regex);
    }

    public boolean isValidUser(User user) {
        if (user == null) {
            return false;
        }
        if (user.getEmail() == null || !isValidEmail(user.getEmail())) {
            return false;
        }
        if (user.getPassword() == null || !isValidPassword(user.getPassword())) {
            return false;
        }
        return true;
    }

    public boolean isValidIdTask(Long idTask) {
        if (idTask == null || idTask.toString().isEmpty()) {
            return false;
        }
        return true;
    }
}


