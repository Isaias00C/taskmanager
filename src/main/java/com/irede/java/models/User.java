package com.irede.java.models;

public interface User {
    
    void status();
    Task getTasksById(int id);
    void createTask();

}
