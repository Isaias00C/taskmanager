package com.irede.java.models;

import com.irede.java.controllers.TaskController;

public class ProjectOwnerUser extends User{
    private TaskController controller;
    
    ProjectOwnerUser(String name, String email){
        super(name, email);
    }

    @Override
    public void status() {
    }

    @Override
    public void createTask() {
        
    }

    public void updateTaskStatus(){
        
    }

}