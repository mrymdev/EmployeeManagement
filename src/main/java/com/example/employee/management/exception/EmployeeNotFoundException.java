package com.example.employee.management.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.NOT_FOUND)
public class EmployeeNotFoundException extends Exception {

    public EmployeeNotFoundException(String id) {
        super(String.format("Employee with id:%s does not exist!", id));
    }
}
