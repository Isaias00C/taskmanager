package com.irede.java.controllers;

import java.util.ArrayList;

import com.irede.java.exceptions.validators.InvalidTaskValidator;
import com.irede.java.exceptions.validators.NullStatusValidator;
import com.irede.java.exceptions.validators.TaskNotFoundValidator;
import com.irede.java.models.TaskModel;
import com.irede.java.models.TaskStatus;
import com.irede.java.repository.TaskRepository;

public class TaskController{
    private TaskRepository repo = new TaskRepository();

    public TaskModel createTask(String owner, String title, String description){
        InvalidTaskValidator.validate(title, description);
         
        TaskModel newTask = new TaskModel(owner, title, description);
        repo.addTaskOnDB(newTask);

        return newTask;
    }
    
    public void updateDescription(String title, String description){
        TaskModel task = getTaskByTitle(title);

        task.setDescription(description);
    }

    public void updateStatus(String title, int option){
        TaskModel task = getTaskByTitle(title);
        
        TaskStatus status = switch(option){
            case 1 -> TaskStatus.EM_ANDAMENTO ;
            case 2 -> TaskStatus.CONCLUIDA;
            default -> null;
        };

        NullStatusValidator.validate(status);

        task.setStatus(status);
    }

    public void getAllTasks(){
        ArrayList<TaskModel> _repo = repo.getRepo();

        for (TaskModel t : _repo){
            System.out.println(t.toString());
        }
    }

    public TaskModel getTaskByTitle(String title){
        TaskModel task = repo.findTaskByTitle(title);

        TaskNotFoundValidator.validate(task);

        return task;
    }
}