package com.irede.java.models;

import javafx.beans.property.*;

public class Task{
    private final IntegerProperty id = new SimpleIntegerProperty();
    private final IntegerProperty assignTo = new SimpleIntegerProperty();
    private final StringProperty title = new SimpleStringProperty();
    private final StringProperty description = new SimpleStringProperty();
    private final ObjectProperty<TaskStatus> status = new SimpleObjectProperty<>(TaskStatus.NAO_INICIADA);

    public Task(int assignTo, String title, String description) {
        this.assignTo.set(assignTo);
        this.title.set(title);
        this.description.set(description);
    }

    @Override
    public String toString() {
        return "[Atribuido à=" + assignTo.get() + ", titulo=" + title.get() + ", descrição=" + description.get() + ", status=" + status.get() + "]";
    }

    public int getAssignTo() {
        return assignTo.get();
    }
    public void setAssignTo(int assignTo) { this.assignTo.set(assignTo); }
    public IntegerProperty assignToProperty() { return assignTo; }

    public String getTitle() {
        return title.get();
    }
    public StringProperty titleProperty() { return title; }

    public String getDescription() {
        return description.get();
    }
    public void setDescription(String description) {
        this.description.set(description);
    }
    public StringProperty descriptionProperty() { return description; }

    public TaskStatus getStatus() {
        return status.get();
    }
    public void setStatus(TaskStatus status) {
        this.status.set(status);
    }
    public ObjectProperty<TaskStatus> statusProperty(){ return status; }

    public int getId() {
        return id.get();
    }

    public IntegerProperty idProperty() {
        return id;
    }
}