package com.irede.java.controllers;

import java.io.IOException;

import com.irede.java.Main;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Hyperlink;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

public class SignInController {

    @FXML private TextField nomeField;
    @FXML private TextField emailField;
    @FXML private PasswordField senhaField;
    @FXML private PasswordField confirmaSenhaField;
    @FXML private ComboBox<String> cargoCombo;
    @FXML private Hyperlink linkLogin;

    @FXML
    public void onSignIn(){
        try {
            Main.setRoot("po-view");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @FXML
    private void onGoToLogin() throws IOException{
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/com/irede/java/views/LoginView.fxml"));
        Parent root = loader.load();

        Stage stage = (Stage) linkLogin.getScene().getWindow();
        stage.setScene(new Scene(root));

    }
}
