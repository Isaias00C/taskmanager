package com.irede.java.controllers;

import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;

import java.util.function.Consumer;

public class TaskFormController {

    @FXML private TextField txtName;
    @FXML private TextArea txtDescription;
    @FXML private ComboBox<String> cmbAssignTo;
    @FXML private ComboBox<String> cmbStatus;
    @FXML private Button btnSave;

    private Consumer<TaskFormData> onSave;
    private Runnable onCancel;

    @FXML
    public void initialize() {
        cmbStatus.getItems().addAll("A Fazer", "Em Andamento", "Concluída");
        cmbAssignTo.getItems().addAll("Todos");
    }

    public void setOnSave(Consumer<TaskFormData> onSave) {
        this.onSave = onSave;
    }

    public void setOnCancel(Runnable onCancel) {
        this.onCancel = onCancel;
    }

    /** Usado no modo edição: pré-preenche os campos com a task selecionada. */
    public void preencher(String nome, String descricao, String status, String atribuido) {
        txtName.setText(nome);
        txtDescription.setText(descricao);
        cmbStatus.setValue(status);
        cmbAssignTo.setValue(atribuido);
        btnSave.setText("Atualizar");
    }

    @FXML
    private void handleSave() {
        if (txtName.getText() == null || txtName.getText().isBlank()) {
            txtName.setStyle("-fx-border-color: red;");
            return;
        }

        TaskFormData dados = new TaskFormData(
                txtName.getText(),
                txtDescription.getText(),
                cmbStatus.getValue(),
                cmbAssignTo.getValue()
        );

        if (onSave != null) onSave.accept(dados);
    }

    @FXML
    private void handleCancel() {
        if (onCancel != null) onCancel.run();
    }

    public record TaskFormData(String name, String description, String status, String urgency) {}
}