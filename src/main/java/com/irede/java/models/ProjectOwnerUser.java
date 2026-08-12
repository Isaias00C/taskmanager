package com.irede.java.models;

import com.irede.java.repository.TaskRepository;

public class ProjectOwnerUser implements User{
    private TaskRepository repo;

    ProjectOwnerUser(){
        repo = new TaskRepository();
    }

    @Override
    public void status() {
    }

    @Override
    public Task getTasksById(int id) {
        return repo.getById(id);
    }

    @Override
    public void createTask() {
        
    }

}