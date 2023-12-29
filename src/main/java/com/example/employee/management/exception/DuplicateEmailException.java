package com.example.employee.management.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.UNPROCESSABLE_ENTITY)
public class DuplicateEmailException extends Exception {

    public DuplicateEmailException(String email) {
        super(String.format("Employee with email:%s already exists!", email));
    }
}
