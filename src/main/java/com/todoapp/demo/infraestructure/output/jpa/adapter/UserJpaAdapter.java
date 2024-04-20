package com.todoapp.demo.infraestructure.output.jpa.adapter;

import com.todoapp.demo.application.exception.ErrorMessagesApplication;
import com.todoapp.demo.application.exception.UserValidationException;
import com.todoapp.demo.domain.model.User;
import com.todoapp.demo.domain.spi.IUserPersistencePort;
import com.todoapp.demo.infraestructure.exception.NoDataFoundException;
import com.todoapp.demo.infraestructure.exception.UserAlreadyExistsException;
import com.todoapp.demo.infraestructure.exception.UserNotFoundException;
import com.todoapp.demo.infraestructure.exceptionHandler.ExceptionResponse;
import com.todoapp.demo.infraestructure.output.jpa.entities.UserEntity;
import com.todoapp.demo.infraestructure.output.jpa.mapper.IUserEntityMapper;
import com.todoapp.demo.infraestructure.output.jpa.repository.IUserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;

@RequiredArgsConstructor
public class UserJpaAdapter implements IUserPersistencePort {

    private final IUserRepository userRepository;
    private final IUserEntityMapper userEntityMapper;

    @Override
    public void createUser(User newUser) {

        try{
            if(userRepository.findById(newUser.getId()).isPresent()){
                throw new UserAlreadyExistsException();
            }



            UserEntity userEntity = userEntityMapper.toEntity(newUser);
            userRepository.save(userEntity);
        }catch(UserAlreadyExistsException e) {
            throw new ResponseStatusException(HttpStatus.CONFLICT, ExceptionResponse.USER_ALREADY_EXISTS.getMessage(), e);
        }catch(UserValidationException e){
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, ErrorMessagesApplication.CANT_CREATE.getMessage(), e);
        }
        catch(Exception e){
            throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR, "Error al crear usuario", e);
        }

//        if(userRepository.findById(newUser.getId()).isPresent()){
//            throw new UserAlreadyExistsException();
//        }
//
//        userRepository.save(userEntityMapper.toEntity(newUser));
    }

    @Override
    public void updateUser(User userToUpdate) {
        if (userRepository.findById(userToUpdate.getId()).isEmpty()){
            throw new UserNotFoundException();
        }

        userRepository.save(userEntityMapper.toEntity(userToUpdate));
    }

    @Override
    public void deleteUser(String id) {
        userRepository.deleteById(id);
    }

    @Override
    public List<User> getUsersByName(String name) {

        if(userRepository.findByName(name).isEmpty()){
            System.out.println("Esta entrando a la validacion");
            throw new NoDataFoundException();
        }

        List<UserEntity> userEntityList = userRepository.findByName(name).get();
        return userEntityMapper.toUserList(userEntityList);
    }

    @Override
    public List<User> getUsersByLastName(String lastName) {
        return userEntityMapper.toUserList(userRepository.findByLastname(lastName).orElseThrow(NoDataFoundException::new));
    }

    @Override
    public List<User> getUsersByTaskId(Long taskId) {
        return userEntityMapper.toUserList(userRepository.findByTaskIds(taskId).orElseThrow(NoDataFoundException::new));
    }

    @Override
    public List<User> getAllUsers() {
        return userEntityMapper.toUserList(userRepository.findAll());
    }

    public User getUserById(String id) {
        //  Consultar el usuario
        User user = userEntityMapper.toUser(userRepository.findById(id).orElseThrow(UserNotFoundException::new));
        //  Consultar en la tabla relación los ids de task que estén relacionados al id del usuario y ponerlos en una lista
        List<Long> taskIdsOfUser = userRepository.getTaskIdsForUser(id);
        //  asignar dicha lista a nuestro user consultado en bbdd
        user.setTasks(taskIdsOfUser);
        return user;

        // return userEntityMapper.toUser(userRepository.findById(id).orElseThrow(UserNotFoundException::new));
    }

    @Override
    public List<User> getUsersByRole(String role) {
        return userEntityMapper.toUserList(userRepository.findByRole(role).orElseThrow(UserNotFoundException::new));
    }
}
