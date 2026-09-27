package com.irede.java.models;

public class Task{
    private final String assignTo;
    private final String title;
    private String description;
    private TaskStatus status;

    public Task(String assignTo, String title, String description) {
        this.assignTo = assignTo;
        this.title = title;
        this.description = description;
        this.status = TaskStatus.NAO_INICIADA;
    }

    @Override
    public String toString() {
        return "[Atribuido à=" + assignTo + ", titulo=" + title + ", descrição=" + description + ", status=" + status + "]";
    }

    public String getassignTo() {
        return assignTo;
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
}