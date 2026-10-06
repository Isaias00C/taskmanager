package com.irede.java.models;

import com.irede.java.utils.Role;

import javafx.beans.property.ObjectProperty;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleObjectProperty;
import javafx.beans.property.SimpleStringProperty;

public abstract class User {
    private final SimpleIntegerProperty id;
    private final SimpleStringProperty name;
    private final SimpleStringProperty email;
    private final SimpleStringProperty password;
    private final ObjectProperty<Role> role;

    protected User(int id, String name, String email, String password, Role role){
        this.id = new SimpleIntegerProperty(id);
        this.name = new SimpleStringProperty(name);
        this.email = new SimpleStringProperty(email);
        this.password = new SimpleStringProperty(password);
        this.role = new SimpleObjectProperty<>(role);

    }

    public SimpleIntegerProperty idProperty() {
        return id;
    }
    public SimpleStringProperty nameProperty() {
        return name;
    }
    public SimpleStringProperty emailProperty() {
        return email;
    }
    public SimpleStringProperty passwordProperty() {
        return password;
    }
    public ObjectProperty<Role> roleProperty() {
        return role;
    }

    public int getId() {
        return id.get();
    }
    public String getName() {
        return name.get();
    }
    public String getEmail() {
        return email.get();
    }
    public String getPassword() {
        return password.get();
    }
    public Role getRole() {
        return role.get();
    }

    public void setName(String name) {
        this.name.set(name);
    }

    public void setEmail(String email) {
        this.email.set(email);
    }

    public void setPassword(String password) {
        this.password.set(password);
    }

    public void setRole(Role role) {
        this.role.set(role);
    }

    public abstract Boolean canCreateTask();
    public abstract Boolean canManageAnyTask();
    public abstract Boolean canSchudelerMeeting();
}
