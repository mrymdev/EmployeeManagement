package com.example.employee.management.msgBroker;

import com.example.employee.management.msgBroker.config.KafkaConfiguration;
import com.example.employee.management.msgBroker.model.EmployeeEventMessage;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.kafka.support.KafkaHeaders;
import org.springframework.messaging.Message;
import org.springframework.messaging.support.MessageBuilder;
import org.springframework.stereotype.Component;

@Component
@Slf4j
@RequiredArgsConstructor
public class EmployeeEventProducer {
    private final KafkaTemplate<String, EmployeeEventMessage> kafkaTransactionTemplate;

    public void sendEmployeeCreationMessage(EmployeeEventMessage employeeEventMessage) {
        log.info(String.format("Employee creation Message sent %s", employeeEventMessage));

        Message<EmployeeEventMessage> message = MessageBuilder
                .withPayload(employeeEventMessage)
                .setHeader(KafkaHeaders.TOPIC, KafkaConfiguration.EMPLOYEE_CREATION)
                .build();

        kafkaTransactionTemplate.send(message);
    }

    public void sendEmployeeUpdateMessage(EmployeeEventMessage employeeEventMessage) {
        log.info(String.format("Employee update Message sent %s", employeeEventMessage));

        Message<EmployeeEventMessage> message = MessageBuilder
                .withPayload(employeeEventMessage)
                .setHeader(KafkaHeaders.TOPIC, KafkaConfiguration.EMPLOYEE_UPDATE)
                .build();

        kafkaTransactionTemplate.send(message);
    }

    public void sendEmployeeDeleteMessage(EmployeeEventMessage employeeEventMessage) {
        log.info(String.format("Employee delete Message sent %s", employeeEventMessage));

        Message<EmployeeEventMessage> message = MessageBuilder
                .withPayload(employeeEventMessage)
                .setHeader(KafkaHeaders.TOPIC, KafkaConfiguration.EMPLOYEE_DELETE)
                .build();

        kafkaTransactionTemplate.send(message);
    }
}
