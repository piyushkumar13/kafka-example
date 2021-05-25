package com.piyush.simplejsonserde;

import com.piyush.AppConfig;
import com.piyush.Student;
import com.piyush.simplejsonserde.serde.JsonSerializer;
import java.util.Properties;
import org.apache.kafka.clients.producer.KafkaProducer;
import org.apache.kafka.clients.producer.ProducerConfig;
import org.apache.kafka.clients.producer.ProducerRecord;
import org.apache.kafka.common.serialization.IntegerSerializer;
import io.confluent.kafka.serializers.json.KafkaJsonSchemaSerializer;

public class Producer {
    public static void main(String[] args) {


        System.out.println("Creating Producer");

        Properties properties = new Properties();

        properties.put(ProducerConfig.CLIENT_ID_CONFIG, AppConfig.APPLICATION_ID); // client id is used to identify the source of the message.
        properties.put(ProducerConfig.BOOTSTRAP_SERVERS_CONFIG, AppConfig.BOOTSTRAP_SERVER);
        properties.put(ProducerConfig.KEY_SERIALIZER_CLASS_CONFIG, IntegerSerializer.class.getName());
        properties.put(ProducerConfig.VALUE_SERIALIZER_CLASS_CONFIG, JsonSerializer.class.getName());

        KafkaProducer<Integer, Student> kafkaProducer = new KafkaProducer<>(properties);

        for (int i = 0; i < AppConfig.numEvents; i++) {
            Student student = new Student();
            student.setId(i);
            student.setName("Piyush" + i);
            student.setAddress("dummyAddress");
            student.setSubject(new String[]{"subject1", "subject2"});

            kafkaProducer.send(new ProducerRecord<>(AppConfig.TOPIC_NAME, i, student));
        }

        kafkaProducer.close();

        System.out.println("Producer is closed");

    }
}
