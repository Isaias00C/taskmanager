package com.irede.java.controllers;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.control.TableView;
import javafx.scene.layout.VBox;

import java.io.IOException;

public class POViewController {

    @FXML private VBox headerContainer;
    @FXML private Button addButton;
    @FXML private Button editButton;
    @FXML private Button deleteButton;
    @FXML private TableView<?> taskTable; // troque <?> pela sua entidade Task

    private Node formNode;
    private TaskFormController formController;

    @FXML
    private void handleAddTask() {
        if (formNode != null) return;

        try {

            abrirFormulario();
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    @FXML
    private void handleEditTask() {
        // Object selecionada = taskTable.getSelectionModel().getSelectedItem();
        // if (selecionada == null) { /* mostrar alerta */ return; }
        // abrirFormulario(selecionada);
    }

    private void abrirFormulario() {
        if (formNode != null) return; // evita abrir dois formulários simultâneos

        try {
            FXMLLoader loader = new FXMLLoader(
                    getClass().getResource("/com/irede/java/views/components/TaskFormComponent.fxml"));
            formNode = loader.load();
            formController = loader.getController();

            formController.setOnSave(this::handleFormSalvar);
            formController.setOnCancel(this::fecharFormulario);

            headerContainer.getChildren().add(formNode);
            setBotoesDesabilitados(true);

        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void handleFormSalvar(TaskFormController.TaskFormData dados) {
        // TODO: persistir via seu DAO/service usando dados.name(), dados.description(),
        // dados.status(), dados.urgency(); depois dar refresh na TableView.

        fecharFormulario();
    }

    private void fecharFormulario() {
        if (formNode != null) {
            headerContainer.getChildren().remove(formNode);
            formNode = null;
            formController = null;
        }
        setBotoesDesabilitados(false);
    }

    private void setBotoesDesabilitados(boolean desabilitado) {
        addButton.setDisable(desabilitado);
        editButton.setDisable(desabilitado);
        deleteButton.setDisable(desabilitado);
    }

    @FXML
    private void handleDeleteTask() { /* ... */ }

    @FXML
    private void handleExit() { /* ... */ }
}