package com.irede.java.controllers;

import com.irede.java.Main;
import com.irede.java.exceptions.AuthException;
import com.irede.java.models.User;
import com.irede.java.services.UserService;
import com.irede.java.utils.Role;
import com.irede.java.utils.Session;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Hyperlink;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

import java.io.IOException;

public class LoginController {

    @FXML private TextField emailField;
    @FXML private PasswordField senhaField;
    @FXML private Hyperlink linkSignIn;

    private final UserService userService = new UserService();

    @FXML
    public void onLogin(){
        try {
            User user = userService.login(emailField.getText(), senhaField.getText());
            Session.setCurrentUser(user);
            Main.setRoot(user.getRole() == Role.PROJECTOWNER ? "POView" : "DevView");
        } catch (AuthException e) {
            new Alert(Alert.AlertType.WARNING, e.getMessage()).showAndWait();
        } catch (IOException | RuntimeException e) {
            e.printStackTrace();
            new Alert(Alert.AlertType.ERROR, "Não foi possível entrar. Verifique a conexão com o banco.").showAndWait();
        }
    }

    @FXML
    public void onGoToSignIn(ActionEvent actionEvent) throws IOException {
        Main.setRoot("SignInView");

    }
}
