package com.irede.java.exceptions.validators;

import com.irede.java.exceptions.NullStatusException;
import com.irede.java.models.TaskStatus;

public class ValidateStatus{
    public ValidateStatus(TaskStatus status){
        if (status == null){
            throw new NullStatusException("Status invalido!");
        }
    }
}