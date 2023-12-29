package com.example.employee.management.service;

import com.example.employee.management.dao.EmployeeRepository;
import com.example.employee.management.exception.DuplicateEmailException;
import com.example.employee.management.exception.EmployeeNotFoundException;
import com.example.employee.management.model.Employee;
import com.example.employee.management.msgBroker.EmployeeEventProducer;
import com.example.employee.management.msgBroker.model.EmployeeEventMessage;
import com.example.employee.management.msgBroker.model.EventType;
import com.mongodb.DuplicateKeyException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.util.ObjectUtils;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class EmployeeService {

    private final EmployeeRepository employeeRepository;

    private final EmployeeEventProducer employeeEventProducer;

    public List<Employee> getEmployees() {
        return employeeRepository.findAll();
    }

    public Employee getEmployee(String id) throws EmployeeNotFoundException {
        if (ObjectUtils.isEmpty(id)) {
            throw new IllegalArgumentException("Employee id should have a value.");
        }
        Optional<Employee> employee = employeeRepository.findById(id);

        return employee.orElseThrow(() -> new EmployeeNotFoundException(id));
    }

    public void deleteEmployee(String id) throws EmployeeNotFoundException {
        if (ObjectUtils.isEmpty(id)) {
            throw new IllegalArgumentException("Employee id should have a value.");
        }
        Optional<Employee> employee = employeeRepository.findById(id);
        if (employee.isEmpty()) {
            throw new EmployeeNotFoundException(id);
        }
        employeeRepository.deleteById(id);
        employeeEventProducer.sendEmployeeDeleteMessage(new EmployeeEventMessage(EventType.DELETE, employee.get()));
    }

    public Employee updateEmployee(Employee employee) throws EmployeeNotFoundException, DuplicateEmailException {
        if (ObjectUtils.isEmpty(employee.getId())) {
            throw new IllegalArgumentException("Employee id should have a value.");
        }
        if (!employeeRepository.existsById(employee.getId())) {
            throw new EmployeeNotFoundException(employee.getId());
        }
        Employee updatedEmployee;
        try {
            updatedEmployee = employeeRepository.save(employee);
        } catch (DuplicateKeyException e) {
            throw new DuplicateEmailException(employee.getEmail());
        }
        employeeEventProducer.sendEmployeeUpdateMessage(new EmployeeEventMessage(EventType.UPDATE, updatedEmployee));
        return updatedEmployee;
    }

    public Employee createEmployee(Employee employee) throws DuplicateEmailException {
        Employee createdEmployee;
        try {
            createdEmployee = employeeRepository.save(employee);
        } catch (DuplicateKeyException e) {
            throw new DuplicateEmailException(employee.getEmail());
        }
        employeeEventProducer.sendEmployeeCreationMessage(new EmployeeEventMessage(EventType.CREATE, createdEmployee));
        return createdEmployee;
    }

}
