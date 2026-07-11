package com.irede.java.controllers;

import com.irede.java.models.TaskModel;
import com.irede.java.repository.TaskRepository;

public class TaskController{
    private TaskRepository repo = new TaskRepository();

    public TaskController(){
        
    }

    public void createTask(String title, String description){
        TaskModel newTask = new TaskModel(title, description);
        repo.addTaskOnDB(newTask);
    }
    
    public void updateDescription(){

    }

    public void updateStatus(){

    }
}