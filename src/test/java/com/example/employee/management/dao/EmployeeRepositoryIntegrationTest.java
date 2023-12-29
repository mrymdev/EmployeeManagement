package com.example.employee.management.dao;

import com.example.employee.management.model.Employee;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.data.mongo.AutoConfigureDataMongo;
import org.springframework.boot.test.autoconfigure.data.mongo.DataMongoTest;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;

import java.time.Instant;
import java.util.Date;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
@ActiveProfiles("test")
@DataMongoTest
@AutoConfigureDataMongo
@Disabled
public class EmployeeRepositoryIntegrationTest {

    @Autowired
    private EmployeeRepository employeeRepository;

    @Test
    public void assertThatRepositoryWorks() {
        String id = "123";
        Employee employee = Employee.builder().id(id).email("test@mail.com").firstName("John")
                .lastName("Doe").birthDate(Date.from(Instant.ofEpochSecond(946684800))).build();

        employeeRepository.save(employee);
        Optional<Employee> foundEmployee = employeeRepository.findById(id);
        assertTrue(foundEmployee.isPresent());
        assertEquals(foundEmployee.get().getId(), id);

        long size = employeeRepository.findAll().spliterator().getExactSizeIfKnown();
        assertTrue(size > 0);

        employeeRepository.deleteById(id);
        foundEmployee = employeeRepository.findById(id);
        assertFalse(foundEmployee.isPresent());

    }

}
