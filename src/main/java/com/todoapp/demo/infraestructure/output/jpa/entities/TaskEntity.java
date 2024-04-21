package com.todoapp.demo.infraestructure.output.jpa.entities;

import com.todoapp.demo.domain.Status;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;
import java.util.List;

@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "tasks")
public class TaskEntity     {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    @Column(nullable = false)
    private String title;

    @Column(nullable = false)
    private String description;

    @Column(name = "start_date", nullable = false)
    private LocalDate startDate;

    @Column(name = "finish_date", nullable = false)
    private LocalDate finishDate;


    @Column(nullable = false)
    @Enumerated(EnumType.STRING)
    private Status status;

    @Column(name = "history_points", nullable = false)
    private Integer historyPoints;

    @PrePersist
    public void prePersist() {
        this.startDate = LocalDate.now();
    }


    @ElementCollection
    @CollectionTable(name = "user_task", joinColumns = @JoinColumn(name = "task_id"))
    @Column(name = "user_id")
    private List<Long> userIds;
}
