package com.todoapp.demo.domain.model;

import com.todoapp.demo.domain.Status;
import org.springframework.cglib.core.Local;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

public class Task {

    private long id;

    private String title;

    private String description;

    private LocalDate startDate;

    private LocalDate finishDate;

    private Status status;

    private Integer historyPoints;

    private List<String> idUsers;

    public Task(long id, String title, String description, LocalDate finishDate, Status status,
                Integer historyPoints, List<String> idUsers) {
        this.id = id;
        this.title = title;
        this.description = description;
        this.startDate = LocalDate.now();
        this.finishDate = finishDate;
        this.status = status;
        this.historyPoints = historyPoints;
        this.idUsers = idUsers;
    }

    public Task(String title, String description, LocalDate startDate, LocalDate finishDate, Status status,
                Integer historyPoints) {
        this.title = title;
        this.description = description;
        this.startDate = startDate;
        this.finishDate = finishDate;
        this.status = status;
        this.historyPoints = historyPoints;
        this.idUsers = new ArrayList<>();
    }

    public Task(){

    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public LocalDate getStartDate() {
        return startDate;
    }

    public void setStartDate(LocalDate startDate) {
        this.startDate = startDate;
    }

    public LocalDate getFinishDate() {
        return finishDate;
    }

    public void setFinishDate(LocalDate finishDate) {
        this.finishDate = finishDate;
    }

    public Status getStatus() {
        return status;
    }

    public void setStatus(Status status) {
        this.status = status;
    }

    public Integer getHistoryPoints() {
        return historyPoints;
    }

    public void setHistoryPoints(Integer historyPoints) {
        this.historyPoints = historyPoints;
    }

    public List<String> getIdUsers() {
        return idUsers;
    }

    public void setIdUsers(List<String> idUsers) {
        this.idUsers = idUsers;
    }

    @Override
    public String toString() {
        return "Task{" +
                "id=" + id +
                ", title='" + title + '\'' +
                ", description='" + description + '\'' +
                ", startDate=" + startDate +
                ", finishDate=" + finishDate +
                ", status=" + status +
                ", historyPoints=" + historyPoints +
                ", idUsers=" + idUsers +
                '}';
    }
}
