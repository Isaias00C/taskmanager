package com.irede.java.models;

import com.irede.java.utils.Role;

public class ProjectOwner extends User{

    private String id;
    private String name;
    private String email;
    private String password;
    private Role role;

    public ProjectOwner(String id, String name, String email, String password) {
        super(id, name, email, password, Role.PROJECTOWNER);
    }

    @Override
    public Boolean canCreateTask() {
        if(role == Role.PROJECTOWNER) return true;
        else return false;
    }

    @Override
    public Boolean canManageAnyTask() {
        if(role == Role.PROJECTOWNER) return true;
        else return false;
    }

    @Override
    public Boolean canSchudelerMeeting() {
        if(role == Role.PROJECTOWNER) return true;
        else return false;
    }

}
