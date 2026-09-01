package com.irede.java.controller;

import javafx.fxml.FXML;
import javafx.scene.control.TextField;

public class PrimaryController {
    
    @FXML
    private TextField txtNome;

    @FXML
    private void adicionarNome(){
        String nome = txtNome.getText().trim();
        System.out.println(">>>>>>> Nome inserido: " + nome);
    }

}
