package com.example.employee.management.msgBroker.model;

import com.example.employee.management.model.Employee;
import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class EmployeeEventMessage {

    private EventType type;

    private Employee employee;

}
