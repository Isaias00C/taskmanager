package com.irede.java.models;

import com.irede.java.utils.Role;

import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleStringProperty;

public abstract class User {
    private final SimpleIntegerProperty id;
    private final SimpleStringProperty name;
    private final String email;
    private final String password;
    private final Role role;

    protected User(int id, String name, String email, String password, Role role){
        this.id = new SimpleIntegerProperty(id);
        this.name = new SimpleStringProperty(name);
        this.email = email;
        this.password = password;
        this.role = role;

    }

    public SimpleIntegerProperty getId() {
        return id;
    }
    public SimpleStringProperty getName() {
        return name;
    }
    public String getEmail() {
        return email;
    }
    public String getPassword() {
        return password;
    }
    public Role getRole() {
        return role;
    }

    public abstract Boolean canCreateTask();
    public abstract Boolean canManageAnyTask();
    public abstract Boolean canSchudelerMeeting();
}
