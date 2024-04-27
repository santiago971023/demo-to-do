package com.todoapp.demo.infraestructure.output.jpa.repository;

import com.todoapp.demo.infraestructure.output.jpa.entities.TaskEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;


public interface ITaskRepository extends JpaRepository<TaskEntity, Long> {

    Optional<List<TaskEntity>> findByStartDate(LocalDate startDate);

    @Query("SELECT t FROM TaskEntity t WHERE MONTH(t.startDate) = :numberMonth")
    Optional<List<TaskEntity>> findTasksByMonth(Integer numberMonth);

    Optional<List<TaskEntity>> findByFinishDate(LocalDate startDate);

    @Query("SELECT t FROM TaskEntity t WHERE :userId IN ELEMENTS(t.userIds) ")
    Optional <List<TaskEntity>> findByUserIds(String userId);

    @Query("SELECT t FROM TaskEntity t WHERE t.status = :status")
    Optional<List<TaskEntity>> findByStatus(@Param("status")String status);

    @Query(value = "SELECT t.user_id FROM user_task t WHERE t.task_id = :taskId", nativeQuery = true)
    List<String> getUserIdsForTask(@Param("taskId") Long taskId);

    @Query(value = "INSERT INTO user_task (user_id, task_id) VALUES (:userId, :taskId)", nativeQuery = true)
    void assignTaskToUser(String userId, Long taskId);

}
