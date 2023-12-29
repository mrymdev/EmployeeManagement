package com.example.employee.management.rest;

import com.example.employee.management.dao.EmployeeRepository;
import com.example.employee.management.model.Employee;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.MethodOrderer;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestMethodOrder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import java.time.Instant;
import java.util.Date;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
public class EmployeeControllerIntegrationTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private EmployeeRepository employeeRepository;

    @Autowired
    private ObjectMapper objectMapper;


    @Test
    @Order(1)
    public void testGetEmployeesEmpty() throws Exception {
        String endpoint = "/api/v1/employees";
        when(employeeRepository.findAll()).thenReturn(List.of());
        String result = mockMvc
                .perform(get(endpoint))
                .andExpect(status().isNoContent())
                .andReturn()
                .getResponse().getContentAsString();
        String expectedContent = "";
        assertEquals(expectedContent, result);
    }

    @Test
    @Order(2)
    public void testCreateEmployee() throws Exception {
        String endpoint = "/api/v1/employees";
        String employeeStr = """
                {
                  
                  "email": "test@mail.com",
                  "firstName": "John",
                  "lastName": "Doe",
                  "birthDate": "2023-12-29T16:15:38.123Z",
                  "hobbies": [
                  ]
                }
                """;
        Employee employee = Employee.builder().id("employeeId").firstName("John").lastName("Doe").email("test@mail.com")
                .birthDate(Date.from(Instant.ofEpochSecond(946684800))).build();
        when(employeeRepository.save(any())).thenReturn(employee);
        String result = mockMvc
                .perform(post(endpoint).contentType(MediaType.APPLICATION_JSON).content(employeeStr))
                .andExpect(status().isOk())
                .andReturn()
                .getResponse().getContentAsString();

        var employeeResult = objectMapper.readValue(result, Employee.class);
        assertFalse(employeeResult.getId().isEmpty());
        assertEquals("John", employeeResult.getFirstName());
        assertEquals("Doe", employeeResult.getLastName());
        assertEquals("test@mail.com", employeeResult.getEmail());
    }

    @Test
    @Order(3)
    public void testGetEmployeesNotEmpty() throws Exception {
        String endpoint = "/api/v1/employees";
        Employee employee = Employee.builder().id("employeeId").firstName("John").lastName("Doe").email("test@mail.com")
                .birthDate(Date.from(Instant.ofEpochSecond(946684800))).build();
        when(employeeRepository.findAll()).thenReturn(List.of(employee));
        String result = mockMvc
                .perform(get(endpoint))
                .andExpect(status().isOk())
                .andReturn()
                .getResponse().getContentAsString();
        assertFalse(result.isEmpty());
        List<Employee> employeeResult = objectMapper.readValue(result, new TypeReference<List<Employee>>() {
        });
        assertTrue(employeeResult.size() > 0);
    }

}
