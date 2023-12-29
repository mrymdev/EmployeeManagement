package com.example.employee.management.msgBroker;

import com.example.employee.management.model.Employee;
import com.example.employee.management.msgBroker.model.EmployeeEventMessage;
import com.example.employee.management.msgBroker.model.EventType;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.annotation.Bean;
import org.springframework.kafka.test.EmbeddedKafkaBroker;
import org.springframework.kafka.test.EmbeddedKafkaZKBroker;
import org.springframework.kafka.test.context.EmbeddedKafka;

import java.time.Instant;
import java.util.Date;

import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;

@SpringBootTest
@EmbeddedKafka
public class EmployeeEventProducerUnitTest {
    @Bean
    EmbeddedKafkaBroker broker() {
        return new EmbeddedKafkaZKBroker(1)
                .kafkaPorts(9092)
                .brokerListProperty("spring.kafka.bootstrap-servers");
    }

    @Autowired
    EmployeeEventProducer employeeEventProducer;

    @Test
    public void testIfMessageIsSent() {
        Employee employee = Employee.builder().id("123").firstName("John").lastName("Doe").email("test@mail.com")
                .birthDate(Date.from(Instant.ofEpochSecond(946684800))).build();
        EmployeeEventMessage eventMessage = new EmployeeEventMessage(EventType.CREATE, employee);
        assertDoesNotThrow(() -> employeeEventProducer.sendEmployeeCreationMessage(eventMessage));
    }

}
