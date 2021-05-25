package com.piyush.kafkaspringboot;

import com.piyush.kafkaspringboot.domain.AppConfig;
import com.piyush.kafkaspringboot.domain.Student;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.kafka.core.KafkaTemplate;

@SpringBootApplication
public class KafkaSpringBootApplication implements ApplicationRunner {

    @Autowired
    private KafkaTemplate<String, Student> kafkaTemplate;

    public static void main(String[] args) {
        SpringApplication.run(KafkaSpringBootApplication.class, args);
    }

    @Override
    public void run(ApplicationArguments args) throws Exception {

        for (int i = 1; i <= 50; i++) {

            Student student = new Student();
            student.setId(i);
            student.setName("PK" + i);
            student.setAddress("dummyAddress");
            student.setSubject(new String[]{"subject1", "subject2"});

            kafkaTemplate.send(AppConfig.TOPIC_NAME, student);
        }
    }
}
