package com.irede.java.controllers;

import com.irede.java.exceptions.validators.ValidateTask;
import com.irede.java.models.TaskModel;
import com.irede.java.repository.TaskRepository;

public class TaskController{
    private TaskRepository repo = new TaskRepository();

    public TaskController(){
        
    }

    public TaskModel createTask(String title, String description){
        new ValidateTask(title, description);

        TaskModel newTask = new TaskModel(title, description);
        repo.addTaskOnDB(newTask);

        return newTask;
    }
    
    public void updateDescription(){

    }

    public void updateStatus(){

    }

    public void getAllTasks(){

    }

    public TaskModel getTask(String title){
        return repo.findTaskByTitle(title);
    }
}