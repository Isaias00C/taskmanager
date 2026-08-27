package com.irede.java.models;

import com.irede.java.utils.Role;

import javafx.beans.property.SimpleIntegerProperty;

public class DeveloperUser extends User{

    private SimpleIntegerProperty id;
    private String name;
    private String email;
    private String password;
    private Role role;

    public DeveloperUser(int id, String name, String email, String password, Role role) {
        super(id, name, email, password, Role.DEVELOPER);
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
