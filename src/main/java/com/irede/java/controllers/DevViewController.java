package com.irede.java.controllers;

import java.io.IOException;

import com.irede.java.Main;
import com.irede.java.exceptions.InvalidTaskException;
import com.irede.java.models.Task;
import com.irede.java.models.TaskStatus;
import com.irede.java.models.User;
import com.irede.java.services.TaskService;
import com.irede.java.utils.Session;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.VBox;

public class DevViewController {

    @FXML private Label lblUser;
    @FXML private VBox headerContainer;
    @FXML private Button editButton;
    @FXML private Button exitButton;

    @FXML private TableView<Task> taskTable;
    @FXML private TableColumn<Task, String> colTitle;
    @FXML private TableColumn<Task, String> colDescription;
    @FXML private TableColumn<Task, TaskStatus> colStatus;

    private final ObservableList<Task> tasks = FXCollections.observableArrayList();
    private final TaskService taskService = new TaskService();

    private Node formNode;
    private Task editingTask;
    private User user;

    @FXML
    public void initialize() {
        user = Session.getCurrentUser();
        lblUser.setText("Desenvolvedor - " + (user == null ? "" : user.getName()));

        colTitle.setCellValueFactory(new PropertyValueFactory<>("title"));
        colDescription.setCellValueFactory(new PropertyValueFactory<>("description"));
        colStatus.setCellValueFactory(new PropertyValueFactory<>("status"));

        if (user != null) tasks.addAll(taskService.getTasksByUser(user.getId()));
        taskTable.setItems(tasks);
    }

    @FXML
    private void handleEditTask() {
        Task selected = taskTable.getSelectionModel().getSelectedItem();
        if (selected == null) {
            new Alert(Alert.AlertType.WARNING, "Selecione uma tarefa para editar").showAndWait();
            return;
        }
        openForms(selected);
    }

    private void openForms(Task task) {
        if (formNode != null) return;

        try {
            FXMLLoader loader = new FXMLLoader(
                    getClass().getResource("/com/irede/java/views/components/TaskFormComponent.fxml"));
            formNode = loader.load();
            TaskFormController formController = loader.getController();
            editingTask = task;

            formController.preencher(taskService.getTitle(task), taskService.getDescription(task),
                    taskService.getStatus(task), taskService.getAssignTo(task));
            formController.bloquearResponsavel();
            formController.setOnSave(this::handleFormSalvar);
            formController.setOnCancel(this::fecharFormulario);

            headerContainer.getChildren().add(formNode);
            editButton.setDisable(true);
        } catch (IOException e) {
            e.printStackTrace();
            formNode = null;
        }
    }

    private void handleFormSalvar(TaskFormController.TaskFormData dados) {
        try {
            TaskStatus status = TaskStatus.fromLabel(dados.status());
            // o desenvolvedor não troca o responsável: mantém o da própria tarefa
            taskService.updateTask(editingTask, editingTask.getAssignTo(), dados.description(), status);
            taskTable.refresh();
            fecharFormulario();
        } catch (InvalidTaskException e) {
            new Alert(Alert.AlertType.WARNING, e.getMessage()).showAndWait();
        }
    }

    private void fecharFormulario() {
        if (formNode != null) {
            headerContainer.getChildren().remove(formNode);
            formNode = null;
        }
        editingTask = null;
        editButton.setDisable(false);
    }

    @FXML
    private void handleExit() throws IOException {
        Session.clear();
        Main.setRoot("LoginView");
    }
}
