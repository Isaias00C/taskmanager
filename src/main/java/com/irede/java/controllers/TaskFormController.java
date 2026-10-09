package com.irede.java.controllers;

import com.irede.java.services.UserService;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;

import java.util.function.Consumer;

public class TaskFormController {

    private final UserService userService = new UserService();

    @FXML private TextField txtTitle;
    @FXML private TextArea txtDescription;
    @FXML private ComboBox<String> cmbAssignTo;
    @FXML private ComboBox<String> cmbStatus;
    @FXML private Button btnSave;

    private Consumer<TaskFormData> onSave;
    private Runnable onCancel;

    @FXML
    public void initialize() {
        cmbStatus.getItems().addAll("Não Iniciada", "Em Andamento", "Concluída");
        cmbAssignTo.getItems().addAll(userService.getAllNames());
        cmbAssignTo.setValue(UserService.ALL);
    }

    public void setOnSave(Consumer<TaskFormData> onSave) {
        this.onSave = onSave;
    }

    public void setOnCancel(Runnable onCancel) {
        this.onCancel = onCancel;
    }

    public void preencher(String title, String descricao, String status, String atribuido) {
        txtTitle.setText(title);
        txtTitle.setDisable(true);
        txtDescription.setText(descricao);
        cmbStatus.setValue(status);
        cmbAssignTo.setValue(atribuido);
        btnSave.setText("Atualizar");
    }

    @FXML
    private void handleSave() {
        if (txtTitle.getText() == null || txtTitle.getText().isBlank()) {
            txtTitle.setStyle("-fx-border-color: red;");
            return;
        }

        TaskFormData dados = new TaskFormData(
                txtTitle.getText(),
                txtDescription.getText(),
                cmbStatus.getValue(),
                userService.getIdByName(cmbAssignTo.getValue())
        );

        if (onSave != null) onSave.accept(dados);
    }

    @FXML
    private void handleCancel() {
        if (onCancel != null) onCancel.run();
    }

    public record TaskFormData(String title, String description, String status, Integer assignTo) {}
}