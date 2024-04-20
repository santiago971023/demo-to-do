package com.todoapp.demo.infraestructure.output.jpa.repository;

import com.todoapp.demo.infraestructure.output.jpa.entities.UserEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Optional;


public interface IUserRepository extends JpaRepository<UserEntity, String> {

    Optional<List<UserEntity>> findByName(String name);

    Optional<List<UserEntity>> findByLastname(String lastname);

    Optional<List<UserEntity>>findByRole(String role);

    //@Query("SELECT t.taskId FROM user_task t WHERE t.userId = :userId")
    //List<Long> getTaskIdsForUser(@Param("userId") String userId);

    @Query(value = "SELECT t.task_id FROM user_task t WHERE t.user_id = :userId", nativeQuery = true)
    List<Long> getTaskIdsForUser(@Param("userId") String userId);

    Optional<List<UserEntity>> findByTaskIds(Long idTask);

}
