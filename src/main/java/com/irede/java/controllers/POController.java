package com.irede.java.controllers;

import com.irede.java.Main;
import com.irede.java.exceptions.InvalidTaskException;
import com.irede.java.exceptions.validators.InvalidTaskValidator;
import com.irede.java.models.Task;
import com.irede.java.models.TaskStatus;
import com.irede.java.services.TaskService;
import javafx.beans.property.ReadOnlyStringWrapper;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
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

    @FXML private TableView<Task> taskTable;
    @FXML private TableColumn<Task, String> colAssignTo;
    @FXML private TableColumn<Task, String> colTitle;
    @FXML private TableColumn<Task, String> colDescription;
    @FXML private TableColumn<Task, TaskStatus> colStatus;

    private final ObservableList<Task> tasks = FXCollections.observableArrayList();
    private int nextId = 1;

    private Node formNode;
    private TaskFormController formController;
    private final TaskService taskService = new TaskService();

    private Task editingTask;

    @FXML
    public void initialize(){
        colAssignTo.setCellValueFactory(cell -> new ReadOnlyStringWrapper(taskService.getAssignTo(cell.getValue())));
        colTitle.setCellValueFactory(new PropertyValueFactory<>("title"));
        colDescription.setCellValueFactory(new PropertyValueFactory<>("description"));
        colStatus.setCellValueFactory(new PropertyValueFactory<>("status"));

        tasks.addAll(taskService.getAllTasks());
        taskTable.setItems(tasks);
    }

    @FXML
    private void handleAddTask() {
        if (formNode != null) return;

        try {
            openForms(null);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    @FXML
    private void handleEditTask() {
         Task selected = taskTable.getSelectionModel().getSelectedItem();
         if (selected == null) {
             if (selected == null){
                 new Alert(Alert.AlertType.WARNING, "Selecione uma tarefa para editar").showAndWait();
             }
             return;
         }
         openForms(selected);
    }

    private void openForms(Task task) {
        if (formNode != null) return; // evita abrir dois formulários simultâneos


        try {
            FXMLLoader loader = new FXMLLoader(
                    getClass().getResource("/com/irede/java/views/components/TaskFormComponent.fxml"));
            formNode = loader.load();
            formController = loader.getController();
            editingTask = task;

            if (task != null){
                formController.preencher(taskService.getTitle(task), taskService.getDescription(task), taskService.getStatus(task), taskService.getAssignTo(task));
            }

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
            TaskStatus status = TaskStatus.fromLabel(dados.status());

            if(editingTask == null){
                Task task = taskService.createTask(dados.assignTo(), dados.title(), dados.description());
                taskService.updateTask(task, dados.assignTo(), dados.description(), status);
                tasks.add(task);
            }else {
                taskService.updateTask(editingTask, dados.assignTo(), dados.description(), status);
                taskTable.refresh();
            }

            fecharFormulario();
            editingTask = null;
        }catch (InvalidTaskException e){

            e.printStackTrace();
        }

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
    private void handleDeleteTask() {
        Task selected = taskTable.getSelectionModel().getSelectedItem();
        if (selected != null){
            tasks.remove(selected);
        }
    }

    @FXML
    private void handleExit() throws IOException {
        Main.setRoot("LoginView");
    }
}