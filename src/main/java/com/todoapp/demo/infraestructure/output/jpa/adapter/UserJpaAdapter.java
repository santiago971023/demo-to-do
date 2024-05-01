package com.todoapp.demo.infraestructure.output.jpa.adapter;

import com.todoapp.demo.application.exception.ErrorMessagesApplication;
import com.todoapp.demo.application.exception.user.UserValidationException;
import com.todoapp.demo.domain.exception.user.UserValidationExceptionDomain;
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
import java.util.Optional;

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
        }catch (UserValidationExceptionDomain e){
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, e.getMessage(), e);
        }
        catch(Exception e){
            throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR, "Error al crear usuario", e);
        }
    }

    @Override
    public void updateUser(User userToUpdate) {

        Optional<UserEntity> user = userRepository.findById(userToUpdate.getId());
        try {

            //Se actualiza el la validacion ya que la consulta debe validar el null,
            //lo cual genera un error al momento de actualizar
            if (user.isPresent()) {
                UserEntity userEntity = user.get();
                userEntity.setName(userToUpdate.getName());
                userEntity.setLastname(userToUpdate.getLastname());
                userEntity.setEmail(userToUpdate.getEmail());
                userEntity.setPassword(userToUpdate.getPassword());
                userEntity.setRole(userToUpdate.getRole());
                if (userToUpdate.getTasks() != null && !userToUpdate.getTasks().isEmpty()){
                    userEntity.setTaskIds(userToUpdate.getTasks());
                }
                userRepository.save(userEntity);
            } else {
                throw new UserNotFoundException();
            }
        }
        catch (UserNotFoundException e) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, ExceptionResponse.USER_NOT_FOUND.getMessage(), e);
        } catch (UserValidationException e) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, ErrorMessagesApplication.CANT_UPDATE.getMessage(), e);
        }catch (Exception e){
            e.printStackTrace();
            throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR, "Error al actualizar usuario", e);
        }
    }
    @Override
    public void deleteUser(String id) {
        try{
            if(userRepository.findById(id).isEmpty()){
                throw new UserNotFoundException();
            }
            userRepository.deleteById(id);
        }catch(UserNotFoundException e){
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, ExceptionResponse.USER_NOT_FOUND.getMessage(), e);
        }catch (UserValidationException e) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, ErrorMessagesApplication.CANT_DELETE.getMessage(), e);
        }catch (Exception e){
            throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR, "Error al eliminar usuario", e);
        }
    }

    @Override
    public List<User> getUsersByName(String name) {
        if(userRepository.findByName(name).isEmpty()){
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
    }

    @Override
    public List<User> getUsersByRole(String role) {
        return userEntityMapper.toUserList(userRepository.findByRole(role).orElseThrow(UserNotFoundException::new));
    }
}
