package com.irede.java.controllers;

import javafx.fxml.FXML;

public class POViewController {


    @FXML
    private void handleAddTask(){
        System.out.println("Add Task button clicked");
    }

    @FXML
    private void handleEditTask(){{
        System.out.println("Edit Task button clicked");
    }}

    @FXML
    private void handleDeleteTask(){
        System.out.println("Delete Task button clicked");
    }

    @FXML
    private void handleExit(){
        System.out.println("Exit button clicked");
    }
}
