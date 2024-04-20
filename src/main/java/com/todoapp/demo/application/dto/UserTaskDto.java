package com.todoapp.demo.application.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class UserTaskDto {

    private Long taskId;

    private String userId;

}
