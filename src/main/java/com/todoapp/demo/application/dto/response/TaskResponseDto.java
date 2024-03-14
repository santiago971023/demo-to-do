package com.todoapp.demo.application.dto.response;

import com.todoapp.demo.domain.Status;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;
import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class TaskResponseDto {

    private long id;

    private String title;

    private String description;

    private LocalDate startDate;

    private LocalDate finishDate;

    private Status status;

    private Integer historyPoints;

    private List<String> idUsers;
}
