package com.irede.java.exceptions.validators;

import com.irede.java.exceptions.TaskNotFoundException;
import com.irede.java.models.Task;

public class TaskNotFoundValidator{
    public static void validate(Task task){
        if (task == null){
            throw new TaskNotFoundException("Tarefa não encontada");
        }
    }
}