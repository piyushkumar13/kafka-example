package com.piyush.simpleserde;

import com.piyush.AppConfig;
import java.util.Properties;
import org.apache.kafka.clients.producer.KafkaProducer;
import org.apache.kafka.clients.producer.ProducerConfig;
import org.apache.kafka.clients.producer.ProducerRecord;
import org.apache.kafka.common.serialization.IntegerSerializer;
import org.apache.kafka.common.serialization.StringSerializer;

public class Producer {
    public static void main(String[] args) {


        System.out.println("Creating Producer");

        Properties properties = new Properties();

        properties.put(ProducerConfig.CLIENT_ID_CONFIG, AppConfig.APPLICATION_ID); // client id is used to identify the source of the message.
        properties.put(ProducerConfig.BOOTSTRAP_SERVERS_CONFIG, AppConfig.BOOTSTRAP_SERVER);
        properties.put(ProducerConfig.KEY_SERIALIZER_CLASS_CONFIG, IntegerSerializer.class.getName());
        properties.put(ProducerConfig.VALUE_SERIALIZER_CLASS_CONFIG, StringSerializer.class.getName());

        KafkaProducer<Integer, String> kafkaProducer = new KafkaProducer<>(properties);

        for (int i = 0; i < AppConfig.numEvents; i++) {
            kafkaProducer.send(new ProducerRecord<>(AppConfig.TOPIC_NAME, i, "Simple Example with event number : " + i));
        }

        kafkaProducer.close();

        System.out.println("Producer is closed");

    }
}
