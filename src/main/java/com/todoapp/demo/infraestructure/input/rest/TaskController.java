package com.todoapp.demo.infraestructure.input.rest;

import com.todoapp.demo.application.dto.request.TaskRequestDto;
import com.todoapp.demo.application.dto.response.TaskResponseDto;
import com.todoapp.demo.application.handler.ITaskHandler;
import lombok.RequiredArgsConstructor;
import org.springframework.cglib.core.Local;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.util.List;

@RestController
@RequestMapping("/todo-app/task")
@RequiredArgsConstructor
public class TaskController {

    private final ITaskHandler taskHandler;

    @PostMapping("/save/{idCreator}")
    public ResponseEntity<Void> saveTask(@RequestBody TaskRequestDto taskRequestDto,@PathVariable String idCreator){
        taskHandler.createTask(taskRequestDto, idCreator);
        return ResponseEntity.status(HttpStatus.CREATED).build();
    }

    @PutMapping("/update/{idUpdater}")
    public ResponseEntity<Void> updateTask(@RequestBody TaskRequestDto taskRequestDto, @PathVariable String idUpdater){
        taskHandler.updateTask(taskRequestDto, idUpdater);
        return ResponseEntity.status(HttpStatus.ACCEPTED).build();
        
    }

    @DeleteMapping("/delete/{idTaskToDelete}/{idDeleter}")
    public ResponseEntity<Void> deleteTaskById(@PathVariable Long idTaskToDelete, @PathVariable String idDeleter){
        taskHandler.deleteTask(idTaskToDelete, idDeleter);
        return ResponseEntity.status(HttpStatus.ACCEPTED).build();
        
    }

    @GetMapping("/all")
    public ResponseEntity<List<TaskResponseDto>> getAllTask(){
        return ResponseEntity.ok(taskHandler.getAllTasks());
    }

    @GetMapping("/id/{idTask}")
    public ResponseEntity<TaskResponseDto> getTaskById(@PathVariable Long idTask){
        return ResponseEntity.ok(taskHandler.getTaskById(idTask));
    }
    
    @GetMapping("/task-by-user/{idUser}")
    public ResponseEntity<List<TaskResponseDto>> getTasksByIdUser(@PathVariable String idUser){
        return ResponseEntity.ok(taskHandler.getTaskByIdUser(idUser));
    }

    @GetMapping("/task-by-status/{status}")
    public ResponseEntity<List<TaskResponseDto>> getTasksByStatus(@PathVariable String status){
        return ResponseEntity.ok(taskHandler.getTaskByStatus(status));
    }

    @PutMapping("/update-status/{idTask}/{status}/{idUpdater}")
    public ResponseEntity<Void> updateStatusFromTask(@PathVariable Long idTask, @PathVariable String status, @PathVariable String idUpdater ){
        taskHandler.updateStatusTask(status,idUpdater, idTask);
        return ResponseEntity.status(HttpStatus.ACCEPTED).build();
    }

    @GetMapping("/task-by-start/{date}")
    public ResponseEntity<List<TaskResponseDto>> getTasksByStartDate(@PathVariable LocalDate date){
        return ResponseEntity.ok(taskHandler.getTaskByStartDate(date));
    }

    @GetMapping("/task-by-finish/{date}")
    public ResponseEntity<List<TaskResponseDto>> getTasksByFinishDate(@PathVariable LocalDate date){
        return ResponseEntity.ok(taskHandler.getTaskByFinishDate(date));
    }



   



}
