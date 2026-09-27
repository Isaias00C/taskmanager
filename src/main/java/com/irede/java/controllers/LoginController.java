package com.irede.java.controllers;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Hyperlink;
import javafx.stage.Stage;

import java.io.IOException;

public class LoginController {

    @FXML private Button btnSignIn;
    @FXML private Hyperlink linkSignIn;

    @FXML
    public void onLogin(){

    }

    @FXML
    public void onGoToSignIn(ActionEvent actionEvent) throws IOException {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/com/irede/java/views/SignInView.fxml"));
        Parent root = loader.load();

        Stage stage = (Stage) linkSignIn.getScene().getWindow();
        stage.setScene(new Scene(root));

    }
}
