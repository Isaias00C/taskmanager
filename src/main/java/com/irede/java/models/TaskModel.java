package com.irede.java.models;

public class TaskModel{
    private final String title;
    private String description;
    private TaskStatus status;

    public TaskModel(String title, String description) {
        this.title = title;
        this.description = description;
        this.status = TaskStatus.NAO_INICIADA;
    }

    @Override
    public String toString() {
        return "[titulo=" + title + ", descrição=" + description + ", status=" + status + "]";
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