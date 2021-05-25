package com.piyush.kafkaspringboot;

import com.piyush.kafkaspringboot.domain.AppConfig;
import com.piyush.kafkaspringboot.domain.Student;
import java.util.HashMap;
import java.util.Map;
import org.apache.kafka.clients.producer.ProducerConfig;
import org.apache.kafka.common.serialization.StringSerializer;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.kafka.core.DefaultKafkaProducerFactory;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.kafka.core.ProducerFactory;
import org.springframework.kafka.support.serializer.JsonSerializer;

/**
 * @author Piyush Kumar.
 * @since 26/05/21.
 */

@Configuration
public class KafkaConfiguration {

    @Bean
    public ProducerFactory<String, Student> producerFactory(){

        Map<String, Object> properties = new HashMap<>();
        properties.put(ProducerConfig.CLIENT_ID_CONFIG, AppConfig.APPLICATION_ID);
        properties.put(ProducerConfig.BOOTSTRAP_SERVERS_CONFIG, AppConfig.BOOTSTRAP_SERVER);
        properties.put(ProducerConfig.KEY_SERIALIZER_CLASS_CONFIG, StringSerializer.class);
        properties.put(ProducerConfig.VALUE_SERIALIZER_CLASS_CONFIG, JsonSerializer.class);


        return new DefaultKafkaProducerFactory<>(properties);
    }

    @Bean
    public KafkaTemplate<String, Student> kafkaTemplate(){
        return new KafkaTemplate<>(producerFactory());
    }
}
