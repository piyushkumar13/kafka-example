package com.piyush.kafkaspringboot.consumer;

import com.piyush.kafkaspringboot.domain.Student;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.kafka.annotation.KafkaListener;

@SpringBootApplication
public class KafkaConsumerSpringBootApplication {

    public static void main(String[] args) {
        SpringApplication.run(KafkaConsumerSpringBootApplication.class, args);
    }

    @KafkaListener(topics = {"MY_TOPIC"}, groupId = "MY_GROUP", containerFactory = "studentKafkaListenerContainerFactory")
    public void consume(Student student){
        System.out.println(student);
    }

}
