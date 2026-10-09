package com.irede.java.services;

import java.util.List;

import com.irede.java.exceptions.validators.InvalidTaskValidator;
import com.irede.java.exceptions.validators.NullStatusValidator;
import com.irede.java.exceptions.validators.TaskNotFoundValidator;
import com.irede.java.models.Task;
import com.irede.java.models.TaskStatus;
import com.irede.java.repository.TaskRepository;

public class TaskService{
    private final TaskRepository repo = new TaskRepository();
    private final UserService userService = new UserService();

    public Task createTask(Integer assignTo, String title, String description){
        InvalidTaskValidator.validate(assignTo, title, description);
         
        Task newTask = new Task(assignTo, title, description);
        repo.add(newTask);

        return newTask;
    }
    
    public void updateDescription(String title, String description){
        Task task = getTaskByTitle(title);

        task.setDescription(description);
        repo.update(task);
    }

    public void updateStatus(String title, int option){
        Task task = getTaskByTitle(title);
        
        TaskStatus status = switch(option){
            case 1 -> TaskStatus.EM_ANDAMENTO ;
            case 2 -> TaskStatus.CONCLUIDA;
            default -> null;
        };

        NullStatusValidator.validate(status);

        task.setStatus(status);
        repo.update(task);
    }

    public void updateTask(Task task, Integer assignTo, String description, TaskStatus status){
        InvalidTaskValidator.validate(assignTo, task.getTitle(), description);
        task.setAssignTo(assignTo);
        task.setDescription(description);
        task.setStatus(status);
        repo.update(task);
    }

    public List<Task> getAllTasks(){
        return repo.findAll();
    }

    public Task getTaskByTitle(String title){
        Task task = repo.findTaskByTitle(title);

        TaskNotFoundValidator.validate(task);

        return task;
    }

    public String getTitle(Task t) { return t.getTitle(); }

    public String getDescription(Task t){ return t.getDescription(); }

    public String getStatus(Task t){ return t.getStatus().getLabel(); }

    public List<Task> getTasksByUser(int userId){
        return repo.findByAssignTo(userId);
    }

    public String getAssignTo(Task t) {
        return userService.getName(t.getAssignTo());
    }
}
