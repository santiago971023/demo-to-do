package com.todoapp.demo.infraestructure.output.jpa.entities;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.todoapp.demo.domain.Role;
import com.todoapp.demo.domain.model.Task;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.List;

@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "users")
public class UserEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private String id;

    @Column(nullable = false)
    private String name;

    @Column(nullable = false)
    private String lastname;

    @Column(nullable = false)
    private String email;

    @Column(nullable = false)
    private String password;

    @Enumerated(EnumType.STRING)
    private Role role;

    @ElementCollection
    @CollectionTable(name = "user_task", joinColumns = @JoinColumn(name = "user_id"))
    @Column(name = "task_id")
    private List<Long> taskIds;


}
