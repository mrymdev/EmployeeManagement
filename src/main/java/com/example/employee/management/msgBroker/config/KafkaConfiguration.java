package com.example.employee.management.msgBroker.config;

import lombok.extern.slf4j.Slf4j;
import org.apache.kafka.clients.admin.NewTopic;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.kafka.annotation.EnableKafka;
import org.springframework.kafka.config.TopicBuilder;
import org.springframework.kafka.test.EmbeddedKafkaBroker;
import org.springframework.kafka.test.EmbeddedKafkaZKBroker;

@Slf4j
@Configuration
@EnableKafka
public class KafkaConfiguration {
    public static final String EMPLOYEE_CREATION = "EMPLOYEE_CREATION";
    public static final String EMPLOYEE_UPDATE = "EMPLOYEE_UPDATE";
    public static final String EMPLOYEE_DELETE = "EMPLOYEE_DELETE";

    @Bean
    public NewTopic buildEmployeeCreationTopic() {
        return TopicBuilder.name(EMPLOYEE_CREATION)
                .partitions(1)
                .replicas(1)
                .build();
    }

    @Bean
    public NewTopic buildEmployeeUpdateTopic() {
        return TopicBuilder.name(EMPLOYEE_UPDATE)
                .partitions(2)
                .replicas(1)
                .build();
    }

    @Bean
    public NewTopic buildEmployeeDeleteTopic() {
        return TopicBuilder.name(EMPLOYEE_DELETE)
                .partitions(1)
                .replicas(1)
                .build();
    }

    @Bean
    EmbeddedKafkaBroker broker() {
        return new EmbeddedKafkaZKBroker(1)
                .kafkaPorts(9092)
                .brokerListProperty("spring.kafka.bootstrap-servers"); // override application property
    }

}
