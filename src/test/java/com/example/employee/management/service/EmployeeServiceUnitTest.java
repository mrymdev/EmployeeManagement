package com.example.employee.management.service;

import com.example.employee.management.dao.EmployeeRepository;
import com.example.employee.management.exception.DuplicateEmailException;
import com.example.employee.management.exception.EmployeeNotFoundException;
import com.example.employee.management.model.Employee;
import com.example.employee.management.msgBroker.EmployeeEventProducer;
import com.mongodb.DuplicateKeyException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;

import java.time.Instant;
import java.util.Date;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.when;


@SpringBootTest
public class EmployeeServiceUnitTest {

    @Autowired
    private EmployeeService employeeService;

    @MockBean
    private EmployeeRepository employeeRepository;

    @MockBean
    private EmployeeEventProducer employeeEventProducer;

    private String employeeId;

    private Employee employee;

    @BeforeEach
    public void setUp() {
        doNothing().when(employeeEventProducer).sendEmployeeCreationMessage(any());

        employeeId = "123";
        employee = Employee.builder().id(employeeId).firstName("John").lastName("Doe").email("test@mail.com")
                .birthDate(Date.from(Instant.ofEpochSecond(946684800))).build();
    }

    @Test
    public void createEmployeeSuccessfully() throws DuplicateEmailException {
        when(employeeRepository.save(any())).thenReturn(employee);
        Employee result = employeeService.createEmployee(employee);
        assertEquals(employeeId, result.getId());
    }

    @Test
    public void createEmployeeFailure_duplicateEmail() {
        when(employeeRepository.save(any())).thenThrow(DuplicateKeyException.class);
        assertThrows(DuplicateEmailException.class, () -> employeeService.createEmployee(employee));
    }

    @Test
    public void updateEmployeeSuccessfully() throws DuplicateEmailException, EmployeeNotFoundException {
        when(employeeRepository.save(any())).thenReturn(employee);
        when(employeeRepository.existsById(any())).thenReturn(true);
        Employee result = employeeService.updateEmployee(employee);
        assertEquals(employeeId, result.getId());
    }

    @Test
    public void updateEmployeeFailure_duplicateEmail() {
        when(employeeRepository.save(any())).thenThrow(DuplicateKeyException.class);
        when(employeeRepository.existsById(any())).thenReturn(true);
        assertThrows(DuplicateEmailException.class, () -> employeeService.updateEmployee(employee));
    }

    @Test
    public void deleteEmployeeSuccessfully() {
        when(employeeRepository.findById(any())).thenReturn(Optional.of(employee));
        doNothing().when(employeeRepository).deleteById(any());
        assertDoesNotThrow(() -> employeeService.deleteEmployee(employeeId));
    }

    @Test
    public void deleteEmployeeFailure_NotFound() {
        when(employeeRepository.findById(any())).thenReturn(Optional.empty());
        assertThrows(EmployeeNotFoundException.class, () -> employeeService.deleteEmployee(employeeId));
    }


    @Test
    public void getEmployeeSuccessfully() throws EmployeeNotFoundException {
        when(employeeRepository.findById(any())).thenReturn(Optional.of(employee));
        String id = "123";
        Employee result = employeeService.getEmployee(id);
        assertEquals(employee, result);
    }

    @Test
    public void getEmployeeFailure_NotFound() {
        when(employeeRepository.findById(any())).thenReturn(Optional.empty());
        assertThrows(EmployeeNotFoundException.class, () -> employeeService.getEmployee(employeeId));
    }

}
