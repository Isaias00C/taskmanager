package com.irede.java.exceptions.validators;

import com.irede.java.exceptions.InvalidTaskException;

public class ValidateTask{
    public ValidateTask(String title, String description){
        if (title == null || title.isBlank() || description == null){
            throw new InvalidTaskException("Tafera invalida: titulo ou descrição incorretos");
        }
    }
}