package com.irede.java.controllers;

import com.irede.java.Main;
import java.io.IOException;

import com.irede.java.exceptions.AuthException;
import com.irede.java.services.UserService;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
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

    private final UserService userService = new UserService();

    @FXML
    public void onSignIn(){
        try {
            userService.register(nomeField.getText(), emailField.getText(), senhaField.getText(),
                    confirmaSenhaField.getText(), cargoCombo.getValue());
            new Alert(Alert.AlertType.INFORMATION, "Cadastro realizado! Faça login para continuar.").showAndWait();
            onGoToLogin();
        } catch (AuthException e) {
            new Alert(Alert.AlertType.WARNING, e.getMessage()).showAndWait();
        } catch (IOException | RuntimeException e) {
            e.printStackTrace();
            new Alert(Alert.AlertType.ERROR, "Não foi possível cadastrar. Verifique a conexão com o banco.").showAndWait();
        }
    }

    @FXML
    private void onGoToLogin() throws IOException{
        Main.setRoot("LoginView");

    }
}
