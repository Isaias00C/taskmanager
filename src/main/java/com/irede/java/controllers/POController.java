package com.irede.java.controllers;

import com.irede.java.models.Task;
import com.irede.java.models.TaskStatus;
import com.irede.java.services.TaskService;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

import java.io.IOException;

public class POController {

    @FXML private VBox headerContainer;
    @FXML private Button addButton;
    @FXML private Button editButton;
    @FXML private Button deleteButton;
    @FXML private Button exitButton;

    @FXML private TableView<Task> taskTable; // troque <?> pela sua entidade Task
    @FXML private TableColumn<Task, String> colAssignTo;
    @FXML private TableColumn<Task, String> colTitle;
    @FXML private TableColumn<Task, String> colDescription;
    @FXML private TableColumn<Task, TaskStatus> colStatus;

    private final ObservableList<Task> tasks = FXCollections.observableArrayList();
    private int nextId = 1;

    private Node formNode;
    private TaskFormController formController;
    private final TaskService taskService = new TaskService();

    @FXML
    public void initialize(){
        colAssignTo.setCellValueFactory(new PropertyValueFactory<>("assignTo"));
        colTitle.setCellValueFactory(new PropertyValueFactory<>("title"));
        colDescription.setCellValueFactory(new PropertyValueFactory<>("description"));
        colStatus.setCellValueFactory(new PropertyValueFactory<>("status"));

        taskTable.setItems(tasks);
    }

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
        try {
            Task task = taskService.createTask(dados.assignTo(), dados.title(), dados.description());
            tasks.add(task);
        }catch (Exception e){
            e.printStackTrace();
        }

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
    private void handleExit() throws IOException {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/com/irede/java/views/LoginView.fxml"));
        Parent root = loader.load();

        Stage stage = (Stage) exitButton.getScene().getWindow();
        stage.setScene(new Scene(root));
    }
}