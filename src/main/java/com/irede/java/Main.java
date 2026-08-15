package com.irede.java;

import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.layout.StackPane;
import javafx.stage.Stage;

public class Main extends Application{

    @Override
    public void start(Stage primaryStage) throws Exception {
        Button button = new Button("clique aqui");

        Scene scene = new Scene(new StackPane(button), 400, 300);
        primaryStage.setScene(scene);
        primaryStage.setTitle("Gerenciador de Tarefas");
        primaryStage.show();
    }

    public static void main(String[] args){
        launch(args);
    }
}
