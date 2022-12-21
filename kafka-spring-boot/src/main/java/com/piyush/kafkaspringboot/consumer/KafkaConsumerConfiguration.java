package com.piyush.kafkaspringboot.consumer;

import com.piyush.kafkaspringboot.consumer.listener.MessageListenerImpl;
import com.piyush.kafkaspringboot.domain.AppConfig;
import com.piyush.kafkaspringboot.domain.Student;
import java.util.HashMap;
import java.util.Map;
import org.apache.kafka.clients.consumer.ConsumerConfig;
import org.apache.kafka.clients.producer.ProducerConfig;
import org.apache.kafka.common.TopicPartition;
import org.apache.kafka.common.serialization.StringDeserializer;
import org.apache.kafka.common.serialization.StringSerializer;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.kafka.annotation.EnableKafka;
import org.springframework.kafka.config.ConcurrentKafkaListenerContainerFactory;
import org.springframework.kafka.core.ConsumerFactory;
import org.springframework.kafka.core.DefaultKafkaConsumerFactory;
import org.springframework.kafka.core.DefaultKafkaProducerFactory;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.kafka.core.ProducerFactory;
import org.springframework.kafka.listener.CommonErrorHandler;
import org.springframework.kafka.listener.ConcurrentMessageListenerContainer;
import org.springframework.kafka.listener.DeadLetterPublishingRecoverer;
import org.springframework.kafka.listener.DefaultErrorHandler;
import org.springframework.kafka.listener.MessageListenerContainer;
import org.springframework.kafka.support.ExponentialBackOffWithMaxRetries;
import org.springframework.kafka.support.serializer.JsonDeserializer;
import org.springframework.kafka.support.serializer.JsonSerializer;
import org.springframework.util.backoff.FixedBackOff;

/**
 * @author Piyush Kumar.
 * @since 26/05/21.
 */
@EnableKafka
@Configuration
public class KafkaConsumerConfiguration {


    /**
     * When auto.offset.reset(ConsumerConfig.AUTO_OFFSET_RESET_CONFIG) is earliest, then the consumer group will read all the old messages.
     * When we say old messages - consumer will pick from the last committed read message which would be from the beginning.
     *
     * Ex - lets say there is a consumer - "mygroup" set with auto.offset.reset=earliest - it started reading messages and for some reason consumer goes down.
     * During the time consumer was down, there had been some messages published to the topic. So, when the consumer again starts(auto.offset.reset) all the
     * messages which was published during consumer was down will be consumed. If in this case, auto.offset.reset=latest it will not read the messages published
     * during the consumer was down... any new messages after consumer is up-- those only will be read.
     * */

    @Bean
    public ConsumerFactory<String, Student> consumerFactory() {

        Map<String, Object> properties = new HashMap<>();

//        properties.put(ConsumerConfig.CLIENT_ID_CONFIG, AppConfig.APPLICATION_ID);
        properties.put(ConsumerConfig.BOOTSTRAP_SERVERS_CONFIG, AppConfig.BOOTSTRAP_SERVER);
        properties.put(ConsumerConfig.GROUP_ID_CONFIG, "MY_GROUP");
        properties.put(ConsumerConfig.AUTO_OFFSET_RESET_CONFIG, "earliest"); // default is latest. Other possible value is earliest
        properties.put(ConsumerConfig.KEY_DESERIALIZER_CLASS_CONFIG, StringDeserializer.class);
        properties.put(ConsumerConfig.VALUE_DESERIALIZER_CLASS_CONFIG, JsonDeserializer.class);

        return new DefaultKafkaConsumerFactory<>(properties, new StringDeserializer(), new JsonDeserializer<>(Student.class));
    }

    @Bean
    public ConcurrentKafkaListenerContainerFactory<String, Student> studentKafkaListenerContainerFactory() {
        ConcurrentKafkaListenerContainerFactory<String, Student> containerFactory = new ConcurrentKafkaListenerContainerFactory<>();
        containerFactory.setConsumerFactory(consumerFactory());

        return containerFactory;
    }


    //========================================= error handler with retry container =======================================================//

    @Bean
    public ConsumerFactory<String, Student> consumerFactory2() {

        Map<String, Object> properties = new HashMap<>();

//        properties.put(ConsumerConfig.CLIENT_ID_CONFIG, AppConfig.APPLICATION_ID);
        properties.put(ConsumerConfig.BOOTSTRAP_SERVERS_CONFIG, AppConfig.BOOTSTRAP_SERVER);
        properties.put(ConsumerConfig.GROUP_ID_CONFIG, "MY_GROUP_ERROR_HANDLER");
//        properties.put(ConsumerConfig.AUTO_OFFSET_RESET_CONFIG, "latest"); // default is latest. Other possible value is earliest
        properties.put(ConsumerConfig.KEY_DESERIALIZER_CLASS_CONFIG, StringDeserializer.class);
        properties.put(ConsumerConfig.VALUE_DESERIALIZER_CLASS_CONFIG, JsonDeserializer.class);


        return new DefaultKafkaConsumerFactory<>(properties, new StringDeserializer(), new JsonDeserializer<>(Student.class));
    }
    @Bean
    public ConcurrentKafkaListenerContainerFactory<String, Student> studentKafkaListenerContainerFactoryWithErrorHandler(){

        ConcurrentKafkaListenerContainerFactory<String, Student> containerFactory = new ConcurrentKafkaListenerContainerFactory<>();
        containerFactory.setConsumerFactory(consumerFactory2());

        containerFactory.setCommonErrorHandler(new DefaultErrorHandler(new FixedBackOff(1000L, 2)));

        return containerFactory;
    }



    //========================================= common container =======================================================//

    @Bean
    public ConsumerFactory<String, Student> consumerFactoryCommon() {

        Map<String, Object> properties = new HashMap<>();

//        properties.put(ConsumerConfig.CLIENT_ID_CONFIG, AppConfig.APPLICATION_ID);
        properties.put(ConsumerConfig.BOOTSTRAP_SERVERS_CONFIG, AppConfig.BOOTSTRAP_SERVER);
        properties.put(ConsumerConfig.AUTO_OFFSET_RESET_CONFIG, "earliest"); // default is latest. Other possible value is earliest
        properties.put(ConsumerConfig.KEY_DESERIALIZER_CLASS_CONFIG, StringDeserializer.class);
        properties.put(ConsumerConfig.VALUE_DESERIALIZER_CLASS_CONFIG, JsonDeserializer.class);


        return new DefaultKafkaConsumerFactory<>(properties, new StringDeserializer(), new JsonDeserializer<>(Student.class));
    }
    @Bean
    public ConcurrentKafkaListenerContainerFactory<String, Student> studentKafkaListenerContainerFactoryCommon(){

        ConcurrentKafkaListenerContainerFactory<String, Student> containerFactory = new ConcurrentKafkaListenerContainerFactory<>();
        containerFactory.setConsumerFactory(consumerFactoryCommon());

        return containerFactory;
    }

    //========================================= retry listener handler container =======================================================//

    @Bean
    public ConsumerFactory<String, Student> consumerFactory3() {

        Map<String, Object> properties = new HashMap<>();

//        properties.put(ConsumerConfig.CLIENT_ID_CONFIG, AppConfig.APPLICATION_ID);
        properties.put(ConsumerConfig.BOOTSTRAP_SERVERS_CONFIG, AppConfig.BOOTSTRAP_SERVER);
//        properties.put(ConsumerConfig.AUTO_OFFSET_RESET_CONFIG, "earliest"); // default is latest. Other possible value is earliest
        properties.put(ConsumerConfig.KEY_DESERIALIZER_CLASS_CONFIG, StringDeserializer.class);
        properties.put(ConsumerConfig.VALUE_DESERIALIZER_CLASS_CONFIG, JsonDeserializer.class);


        return new DefaultKafkaConsumerFactory<>(properties, new StringDeserializer(), new JsonDeserializer<>(Student.class));
    }

    public CommonErrorHandler errorHandler(){


        FixedBackOff fixedBackOff = new FixedBackOff(1000L, 2);

        ExponentialBackOffWithMaxRetries expoBackOff = new ExponentialBackOffWithMaxRetries(2);
        expoBackOff.setInitialInterval(1000L);
        expoBackOff.setMultiplier(2);
        expoBackOff.setMaxInterval(10000);

//        DefaultErrorHandler defaultErrorHandler = new DefaultErrorHandler(fixedBackOff);
        DefaultErrorHandler defaultErrorHandler = new DefaultErrorHandler(expoBackOff); // we can also set exponential backoff

        defaultErrorHandler.addNotRetryableExceptions(IllegalArgumentException.class, ArithmeticException.class, NullPointerException.class);

        defaultErrorHandler.setRetryListeners(((record, ex, deliveryAttempt) -> {
            System.out.println("Retry listener called for attempt :::: " + deliveryAttempt);
            System.out.println("The record is :::: " + record);
            System.out.println("The ex is :::: " + ex);
            System.out.println("The deliveryAttempt is :::: " + deliveryAttempt);
        }));

        return defaultErrorHandler;
    }

    @Bean
    public ConcurrentKafkaListenerContainerFactory<String, Student> studentKafkaListenerContainerFactory3(){

        ConcurrentKafkaListenerContainerFactory<String, Student> containerFactory = new ConcurrentKafkaListenerContainerFactory<>();
        containerFactory.setConsumerFactory(consumerFactory3());

        containerFactory.setCommonErrorHandler(errorHandler());

        return containerFactory;
    }


    //========================================= deadletter/retry topic publishing =======================================================//
    // https://docs.spring.io/spring-kafka/docs/current/reference/html/#dead-letters

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

    @Bean
    public ConsumerFactory<String, Student> consumerFactory4() {

        Map<String, Object> properties = new HashMap<>();

//        properties.put(ConsumerConfig.CLIENT_ID_CONFIG, AppConfig.APPLICATION_ID);
        properties.put(ConsumerConfig.BOOTSTRAP_SERVERS_CONFIG, AppConfig.BOOTSTRAP_SERVER);
//        properties.put(ConsumerConfig.AUTO_OFFSET_RESET_CONFIG, "earliest"); // default is latest. Other possible value is earliest
        properties.put(ConsumerConfig.KEY_DESERIALIZER_CLASS_CONFIG, StringDeserializer.class);
        properties.put(ConsumerConfig.VALUE_DESERIALIZER_CLASS_CONFIG, JsonDeserializer.class);


        return new DefaultKafkaConsumerFactory<>(properties, new StringDeserializer(), new JsonDeserializer<>(Student.class));
    }


    public DeadLetterPublishingRecoverer deadLetterPublishingRecoverer() {

        DeadLetterPublishingRecoverer deadLetterPublishingRecoverer = new DeadLetterPublishingRecoverer(kafkaTemplate(),

                (r, e) -> {
                    if (e instanceof RuntimeException) {
                        System.out.println("Dead letter runtime exception");
                        System.out.println("Received record is ::: " + r);
                        return new TopicPartition("MY_TOPIC_3", r.partition());
                    } else {
                        return new TopicPartition("MY_TOPIC_ANOTHER.DLT", r.partition());
                    }

                }

        );

        return deadLetterPublishingRecoverer;
    }


    public CommonErrorHandler errorHandler1(){


        FixedBackOff fixedBackOff = new FixedBackOff(1000L, 2);


        DefaultErrorHandler defaultErrorHandler = new DefaultErrorHandler(deadLetterPublishingRecoverer(), fixedBackOff);

        defaultErrorHandler.addNotRetryableExceptions(IllegalArgumentException.class, ArithmeticException.class, NullPointerException.class);

        defaultErrorHandler.setRetryListeners(((record, ex, deliveryAttempt) -> {
            System.out.println("Retry listener called for attempt :::: " + deliveryAttempt);
            System.out.println("The record is :::: " + record);
            System.out.println("The ex is :::: " + ex);
            System.out.println("The deliveryAttempt is :::: " + deliveryAttempt);
        }));


        return defaultErrorHandler;
    }
    @Bean
    public ConcurrentKafkaListenerContainerFactory<String, Student> studentKafkaListenerContainerFactory4(){

        ConcurrentKafkaListenerContainerFactory<String, Student> containerFactory = new ConcurrentKafkaListenerContainerFactory<>();
        containerFactory.setConsumerFactory(consumerFactory4());

        containerFactory.setCommonErrorHandler(errorHandler1());

        return containerFactory;
    }


    //========================================= Record filtering =======================================================//


    @Bean
    public ConsumerFactory<String, Student> consumerFactory5() {

        Map<String, Object> properties = new HashMap<>();

//        properties.put(ConsumerConfig.CLIENT_ID_CONFIG, AppConfig.APPLICATION_ID);
        properties.put(ConsumerConfig.BOOTSTRAP_SERVERS_CONFIG, AppConfig.BOOTSTRAP_SERVER);
//        properties.put(ConsumerConfig.AUTO_OFFSET_RESET_CONFIG, "earliest"); // default is latest. Other possible value is earliest
        properties.put(ConsumerConfig.KEY_DESERIALIZER_CLASS_CONFIG, StringDeserializer.class);
        properties.put(ConsumerConfig.VALUE_DESERIALIZER_CLASS_CONFIG, JsonDeserializer.class);


        return new DefaultKafkaConsumerFactory<>(properties, new StringDeserializer(), new JsonDeserializer<>(Student.class));
    }

    @Bean
    public ConcurrentKafkaListenerContainerFactory<String, Student> studentKafkaListenerContainerFactory5(){

        ConcurrentKafkaListenerContainerFactory<String, Student> containerFactory = new ConcurrentKafkaListenerContainerFactory<>();
        containerFactory.setConsumerFactory(consumerFactory5());
        containerFactory.setRecordFilterStrategy(consumerRecord -> {
            System.out.println("Filtering out record with " + consumerRecord.value());
            return consumerRecord.value().getName().equals("PK1");
        });
        containerFactory.setRecordInterceptor(record -> {
            System.out.println("Intercepting record :::: " + record);
            return record;
        });

        return containerFactory;
    }

    //========================================= Programmatic listeners type 1 =======================================================//

//    @Bean
//    public MessageListenerContainer messageListenerContainer() {
//
//        Map<String, Object> properties = new HashMap<>();
//
////        properties.put(ConsumerConfig.CLIENT_ID_CONFIG, AppConfig.APPLICATION_ID);
//        properties.put(ConsumerConfig.BOOTSTRAP_SERVERS_CONFIG, AppConfig.BOOTSTRAP_SERVER);
////        properties.put(ConsumerConfig.GROUP_ID_CONFIG, "PROGRAMMATIC_LISTENER");
////        properties.put(ConsumerConfig.AUTO_OFFSET_RESET_CONFIG, "earliest"); // default is latest. Other possible value is earliest
//        properties.put(ConsumerConfig.KEY_DESERIALIZER_CLASS_CONFIG, StringDeserializer.class);
//        properties.put(ConsumerConfig.VALUE_DESERIALIZER_CLASS_CONFIG, JsonDeserializer.class);
//
//
//        DefaultKafkaConsumerFactory<String, Student> consumerFactory = new DefaultKafkaConsumerFactory<>(properties, new StringDeserializer(), new JsonDeserializer<>(Student.class));
//
//        ContainerProperties containerProperties = new ContainerProperties("MY_TOPIC");
//        containerProperties.setMessageListener(new MessageListenerImpl());
//        containerProperties.setGroupId("PROGRAMMATIC_LISTENER"); // we can either set it here or at the consumer properties as set above.
//
//        return new KafkaMessageListenerContainer<>(consumerFactory, containerProperties); // I can also use ConcurrentMessageListenerContainer
//    }

    //========================================= Programmatic listeners type 2 =======================================================//

    @Bean
    public MessageListenerContainer messageListenerContainer2() {

        Map<String, Object> properties = new HashMap<>();

//        properties.put(ConsumerConfig.CLIENT_ID_CONFIG, AppConfig.APPLICATION_ID);
        properties.put(ConsumerConfig.BOOTSTRAP_SERVERS_CONFIG, AppConfig.BOOTSTRAP_SERVER);
//        properties.put(ConsumerConfig.GROUP_ID_CONFIG, "PROGRAMMATIC_2_LISTENER");
//        properties.put(ConsumerConfig.AUTO_OFFSET_RESET_CONFIG, "earliest"); // default is latest. Other possible value is earliest
        properties.put(ConsumerConfig.KEY_DESERIALIZER_CLASS_CONFIG, StringDeserializer.class);
        properties.put(ConsumerConfig.VALUE_DESERIALIZER_CLASS_CONFIG, JsonDeserializer.class);


        DefaultKafkaConsumerFactory<String, Student> consumerFactory = new DefaultKafkaConsumerFactory<>(properties, new StringDeserializer(), new JsonDeserializer<>(Student.class));

        ConcurrentKafkaListenerContainerFactory<String, Student> containerFactory = new ConcurrentKafkaListenerContainerFactory<>();
        containerFactory.setConsumerFactory(consumerFactory);

        ConcurrentMessageListenerContainer<String, Student> listenerContainer = containerFactory.createContainer("MY_TOPIC");

        listenerContainer.getContainerProperties().setMessageListener(new MessageListenerImpl());
        listenerContainer.getContainerProperties().setGroupId("PROGRAMMATIC_2_LISTENER");

        return listenerContainer;
    }
}
