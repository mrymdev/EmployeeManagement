package com.example.employee.management.rest;

import com.example.employee.management.exception.DuplicateEmailException;
import com.example.employee.management.exception.EmployeeNotFoundException;
import com.example.employee.management.model.Employee;
import com.example.employee.management.service.EmployeeService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api")
public class EmployeeController {

    private final EmployeeService employeeService;

    @GetMapping(value = "/v1/employees")
    public ResponseEntity<List<Employee>> getEmployees() {
        List<Employee> employees = employeeService.getEmployees();
        if (employees.isEmpty()) return ResponseEntity.noContent().build();
        return ResponseEntity.ok().body(employees);
    }

    @GetMapping(value = "/v1/employees/{id}")
    public ResponseEntity<Employee> getEmployee(@PathVariable String id) throws EmployeeNotFoundException {
        Employee employee = employeeService.getEmployee(id);
        return ResponseEntity.ok().body(employee);
    }

    @DeleteMapping("/v1/employees/{id}")
    public ResponseEntity<Object> deleteEmployee(@PathVariable String id) throws EmployeeNotFoundException {
        employeeService.deleteEmployee(id);
        return ResponseEntity.ok().build();
    }

    @PutMapping("/v1/employees/{id}")
    public ResponseEntity<Object> updateEmployee(@RequestBody Employee employee) throws EmployeeNotFoundException, DuplicateEmailException {
        Employee updatedEmployee = employeeService.updateEmployee(employee);
        return ResponseEntity.ok().body(updatedEmployee);
    }

    @PostMapping("/v1/employees")
    public ResponseEntity<Employee> createEmployee(@RequestBody Employee employee) throws DuplicateEmailException {
        Employee newEmployee = employeeService.createEmployee(employee);
        return ResponseEntity.ok().body(newEmployee);
    }

}
