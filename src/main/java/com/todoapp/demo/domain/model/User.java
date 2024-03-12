package com.todoapp.demo.domain.model;

import com.todoapp.demo.domain.Role;

import java.util.List;

public class User {

    private String id;

    private String name;

    private String lastname;

    private String email;

    private String password;

    private Role role;

    private List<Long> tasks;

    public User(String id, String name, String lastname, String email, String password, Role role, List<Long> tasks) {
        this.id = id;
        this.name = name;
        this.lastname = lastname;
        this.email = email;
        this.password = password;
        this.role = role;
        this.tasks = tasks;
    }

    public User() {
    }

    public User(String name, String lastname, String email, String password, Role role) {

        this.name = name;
        this.lastname = lastname;
        this.email = email;
        this.password = password;
        this.role = role;
    }


    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getLastname() {
        return lastname;
    }

    public void setLastname(String lastname) {
        this.lastname = lastname;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public Role getRole() {
        return role;
    }

    public void setRole(Role role) {
        this.role = role;
    }

    public List<Long> getTasks() {
        return tasks;
    }

    public void setTasks(List<Long> tasks) {
        this.tasks = tasks;
    }
}
