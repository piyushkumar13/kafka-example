package com.piyush.simplejsonserde;

import static java.util.Arrays.asList;

import com.piyush.AppConfig;
import com.piyush.Student;
import com.piyush.simplejsonserde.serde.JsonDeserializer;
import java.time.Duration;
import java.util.Properties;
import org.apache.kafka.clients.consumer.ConsumerConfig;
import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.apache.kafka.clients.consumer.ConsumerRecords;
import org.apache.kafka.clients.consumer.KafkaConsumer;
import org.apache.kafka.common.serialization.IntegerDeserializer;
import io.confluent.kafka.serializers.KafkaJsonDeserializer;

public class Consumer {
    public static void main(String[] args) {


        System.out.println("Creating Consumer");

        Properties properties = new Properties();

        properties.put(ConsumerConfig.CLIENT_ID_CONFIG, AppConfig.APPLICATION_ID); // client id is used to identify the source of the message.
        properties.put(ConsumerConfig.BOOTSTRAP_SERVERS_CONFIG, AppConfig.BOOTSTRAP_SERVER);
        properties.put(ConsumerConfig.KEY_DESERIALIZER_CLASS_CONFIG, IntegerDeserializer.class.getName());
        properties.put(ConsumerConfig.VALUE_DESERIALIZER_CLASS_CONFIG, JsonDeserializer.class.getName());
        properties.put(JsonDeserializer.VALUE_CLASS_NAME_CONFIG, Student.class);
        properties.put(ConsumerConfig.GROUP_ID_CONFIG, AppConfig.GROUP_ID);
        properties.put(ConsumerConfig.AUTO_OFFSET_RESET_CONFIG, "earliest");

        KafkaConsumer<Integer, String> kafkaConsumer = new KafkaConsumer<>(properties);

        kafkaConsumer.subscribe(asList(AppConfig.TOPIC_NAME));

        while (true) {
            ConsumerRecords<Integer, String> consumerRecords = kafkaConsumer.poll(Duration.ofMillis(1000));

            for (ConsumerRecord record : consumerRecords) {
                System.out.println("The consumed record is :::: ");
                System.out.println(record.value());
                System.out.println(":::::::::: Record ends here :::::::: ");
            }
        }
    }
}
