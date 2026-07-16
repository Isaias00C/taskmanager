package com.irede.java.exceptions.validators;

import com.irede.java.exceptions.NullStatusException;
import com.irede.java.models.TaskStatus;

public class NullStatusValidator{
    public static void validate(TaskStatus status){
        if (status == null){
            throw new NullStatusException("Status invalido!");
        }
    }
}