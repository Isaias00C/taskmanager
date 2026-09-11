package com.irede.java.controllers;

import java.io.IOException;

import com.irede.java.Main;

import javafx.fxml.FXML;

public class SignInController {

    @FXML
    public void onSignIn(){
        try {
            Main.setRoot("po-view");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
