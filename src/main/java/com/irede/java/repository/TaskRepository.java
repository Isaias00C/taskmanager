package com.irede.java.repository;

import java.util.ArrayList;
import com.irede.java.models.TaskModel;

public class TaskRepository{
    private ArrayList<TaskModel> repo;

    public TaskRepository() {
        this.repo = new ArrayList<TaskModel>();       
    }

    public boolean addTaskOnDB(TaskModel task){
        return repo.add(task);
    }

    public boolean deleteTask(TaskModel task){
        return repo.remove(task);
    }
}