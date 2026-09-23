package com.irede.java.controllers;

import java.io.IOException;

import com.irede.java.Main;

import javafx.fxml.FXML;
import javafx.scene.control.ComboBox;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;

public class SignInController {

    @FXML private TextField nomeField;
    @FXML private TextField emailField;
    @FXML private PasswordField senhaField;
    @FXML private PasswordField confirmaSenhaField;
    @FXML private ComboBox<String> cargoCombo;


    @FXML
    public void onSignIn(){
        try {
            Main.setRoot("po-view");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @FXML
    private void onGoToLogin() {
        // trocar para a tela de login
    }
}
