package com.irede.java;

import java.io.IOException;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

public class Main extends Application {

    private static Scene scene;
    private static final String VIEW = "/com/irede/java/views/";

    @Override
    public void start(Stage stage) throws IOException {
        scene = new Scene(loadFXML("SignInView"));
        scene.getStylesheets().add(
                getClass().getResource("/css/styles.css").toExternalForm()
        );
        stage.setScene(scene);
        stage.show();
    }

    public static void setRoot(String fxml) throws IOException{
        scene.setRoot(loadFXML(fxml));
    }

    private static Parent loadFXML(String fxml) throws IOException{
        FXMLLoader fxmlLoader = new FXMLLoader(Main.class.getResource(VIEW + fxml + ".fxml"));
        return fxmlLoader.load();

    }

    public static void main(String[] args) {
        launch(args);
    }
}
