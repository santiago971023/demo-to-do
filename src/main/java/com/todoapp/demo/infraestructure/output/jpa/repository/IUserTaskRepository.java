package com.todoapp.demo.infraestructure.output.jpa.repository;

import com.todoapp.demo.infraestructure.output.jpa.entities.UserTaskEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface IUserTaskRepository extends JpaRepository<UserTaskEntity, Long> {

    @Query("SELECT ut.taskId FROM UserTaskEntity ut WHERE ut.userId = :userId")
    List<Long> findTaskIdsByUserId(@Param("userId") String userId);

    @Query("SELECT ut.userId FROM UserTaskEntity ut WHERE ut.taskId = :taskId")
    List<String> findUserIdsByTaskId(@Param("taskId") Long taskId);

    @Modifying
    @Query(value = "DELETE FROM user_task WHERE user_id = :userId AND task_id = :taskId", nativeQuery = true)
    void removeTaskFromUser(String userId, Long taskId);

    @Modifying
    @Query(value = "INSERT INTO user_task (user_id, task_id) VALUES (:userId, :taskId)", nativeQuery = true)
    void assignTaskToUser(String userId, Long taskId);

}
