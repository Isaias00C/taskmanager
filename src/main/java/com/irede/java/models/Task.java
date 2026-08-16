package com.irede.java.models;

import java.util.List;

public class Task{
    private final String owner;
    private final String title;
    private String description;
    private TaskStatus status;
    private List<User> assignedUsers;

    public Task(String owner, String title, String description) {
        this.owner = owner;
        this.title = title;
        this.description = description;
        this.status = TaskStatus.NAO_INICIADA;
    }

    @Override
    public String toString() {
        return "[Dono=" + owner + ", titulo=" + title + ", descrição=" + description + ", status=" + status + "]";
    }

    public String getOwner() {
        return owner;
    }

    public String getTitle() {
        return title;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public TaskStatus getStatus() {
        return status;
    }

    public void setStatus(TaskStatus status) {
        this.status = status;
    }

    public void assignUser(User user){
        assignedUsers.add(user);
    }

    public List<User> getAssignedUsers() {
        return assignedUsers;
    }
    
}