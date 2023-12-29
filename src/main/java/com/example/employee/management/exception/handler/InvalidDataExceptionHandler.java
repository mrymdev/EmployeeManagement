package com.example.employee.management.exception.handler;

import com.example.employee.management.exception.DuplicateEmailException;
import com.example.employee.management.exception.EmployeeNotFoundException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class InvalidDataExceptionHandler {

    /**
     * Exception handler for invalid employee id.
     *
     * @return HTTP Status 404
     */
    @ExceptionHandler(EmployeeNotFoundException.class)
    @ResponseStatus(HttpStatus.NOT_FOUND)
    protected ResponseEntity<Object> handleRequestInvalidEmployee() {
        return new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }

    /**
     * Exception handler for duplicate email.
     *
     * @return HTTP Status 422
     */
    @ExceptionHandler(DuplicateEmailException.class)
    @ResponseStatus(HttpStatus.UNPROCESSABLE_ENTITY)
    protected ResponseEntity<Object> handleRequestDuplicateEmployee() {
        return new ResponseEntity<>(HttpStatus.UNPROCESSABLE_ENTITY);
    }
}
