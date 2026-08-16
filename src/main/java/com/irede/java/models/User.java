package com.irede.java.models;

public abstract class User {
    String name;
    String email;

    User(String name, String email){
        this.name = name;
        this.email = email;
    }

    void status(){
        System.out.println(name);
        System.out.println(email);
    }
    
    void createTask(){

    }

}
