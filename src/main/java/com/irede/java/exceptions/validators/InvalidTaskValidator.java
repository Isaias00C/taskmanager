package com.irede.java.exceptions.validators;

import com.irede.java.exceptions.InvalidTaskException;

public class InvalidTaskValidator{
    public static void validate(int assignTo, String title, String description){
        if (assignTo == null || title == null || title.isBlank() || description == null){
            throw new InvalidTaskException("Tafera invalida: titulo ou descrição invalidos");
        }
    }
}