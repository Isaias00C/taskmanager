package com.irede.java.exceptions.validators;

import com.irede.java.exceptions.TaskNotFoundException;
import com.irede.java.models.TaskModel;

public class TaskNotFoundValidator{
    public static void validate(TaskModel task){
        if (task == null){
            throw new TaskNotFoundException("Tarefa não encontada");
        }
    }
}