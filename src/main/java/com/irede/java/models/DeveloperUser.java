package com.irede.java.models;

import com.irede.java.utils.Role;

public class DeveloperUser extends User{

    public DeveloperUser(int id, String name, String email, String password) {
        super(id, name, email, password, Role.DEVELOPER);
    }

    @Override
    public Boolean canCreateTask() {
        return getRole() == Role.PROJECTOWNER;
    }

    @Override
    public Boolean canManageAnyTask() {
        return getRole() == Role.PROJECTOWNER;
    }

    @Override
    public Boolean canSchudelerMeeting() {
        return getRole() == Role.PROJECTOWNER;
    }

}
