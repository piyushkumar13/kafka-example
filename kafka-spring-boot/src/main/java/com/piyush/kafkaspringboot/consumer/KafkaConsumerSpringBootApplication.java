package com.piyush.kafkaspringboot.consumer;

import com.piyush.kafkaspringboot.domain.Student;
import java.nio.ByteBuffer;
import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.kafka.support.KafkaHeaders;
import org.springframework.messaging.handler.annotation.Header;

@SpringBootApplication
public class KafkaConsumerSpringBootApplication {

    public static void main(String[] args) {
        SpringApplication.run(KafkaConsumerSpringBootApplication.class, args);
    }

    /* If we have given a ConsumerConfig.GROUP_ID_CONFIG in container factory and we are providing group id attribute in @kafkalistener annotation. Then,
     *  group id in @KafkaListener annotation will take precedence. Therefore, even if we provide ConsumerConfig.GROUP_ID_CONFIG in container factory, then also
     *  we can use that container factory commonly with multiple @KafkaListener just we need to add the group id attribute in @KafkaListener*/
//    @KafkaListener(topics = {"MY_TOPIC"}, groupId = "MY_GROUP", containerFactory = "studentKafkaListenerContainerFactory")
//    public void consume1(Student student) {
//        System.out.println("Consuming with simple container factory");
//        System.out.println(student);
//    }

//    @KafkaListener(topics = {"MY_TOPIC"}, groupId = "MY_GROUP", containerFactory = "studentKafkaListenerContainerFactory")
//    public void consume2(Student student) {
//        System.out.println("Consuming with simple container factory again");
//        System.out.println(student);
//    }


    //============================================== Container with error handler =====================================//
//
//    @KafkaListener(topics = "MY_TOPIC", groupId = "MY_GROUP_ERROR_HANDLER", containerFactory = "studentKafkaListenerContainerFactoryWithErrorHandler")
//    public void consume3(Student student) {
//        System.out.println("Consuming with error handler to retry");
//
//        throw new RuntimeException("Consuming with error handler to retry");
//    }


    //============================================== Using common containers ==========================================//

    /* group id on the listener lets you create different consumer group but allowing you to use the same container factory.
     *  NOTE : if we want to use common container factory then make sure not to set ConsumerConfig.GROUP_ID_CONFIG in consumer factory which is
     *  then set in container factory */
//    @KafkaListener(topics = {"MY_TOPIC"}, groupId = "MY_GROUP_COMMON-1", containerFactory = "studentKafkaListenerContainerFactoryCommon")
//    public void consume2(Student student){
//        System.out.println("Consuming with common container factory with group id : MY_GROUP_COMMON-1");
//        System.out.println(student);
//    }
//
//    @KafkaListener(topics = {"MY_TOPIC"}, groupId = "MY_GROUP_COMMON-2", containerFactory = "studentKafkaListenerContainerFactoryCommon")
//    public void consume3(Student student){
//        System.out.println("Consuming with common container factory with group id : MY_GROUP_COMMON-2");
//        System.out.println(student);
//    }

//    //============================================== Using retry listener handler container ==========================================//
//
//    @KafkaListener(topics = {"MY_TOPIC"}, groupId = "MY_LISTENER_GROUP", containerFactory = "studentKafkaListenerContainerFactory3")
//    public void consume4(Student student) {
//        System.out.println("Consuming with listener container factory");
//        System.out.println(student);
//
//        throw new RuntimeException("Consuming with listener container factory");
////        throw new IllegalArgumentException("Consuming with listener container factory");
//    }

    //============================================== Using deadletter container ==========================================//

//    @KafkaListener(topics = {"MY_TOPIC"}, groupId = "MY_RETRY_GROUP_EXP", containerFactory = "studentKafkaListenerContainerFactory4")
//    public void consume5(ConsumerRecord<String, Student> studentRecord, @Header(KafkaHeaders.OFFSET) int offset) {
//        System.out.println("Consuming with listener and retry container factory for deadletter example");
//        System.out.println(studentRecord);
//
//        throw new RuntimeException("Consuming with listener and retry container factory for deadletter example");
////        throw new IllegalArgumentException("Consuming with listener container factory");
//    }
//
//    @KafkaListener(topics = {"MY_TOPIC_3"}, groupId = "MY_DLT_RETRY_GROUP", containerFactory = "studentKafkaListenerContainerFactory4")
//    public void consume6(ConsumerRecord<String, Student> studentRecord, @Header(KafkaHeaders.DLT_ORIGINAL_TOPIC) String orgTopic, @Header(KafkaHeaders.DLT_ORIGINAL_OFFSET) Long orgOffset) {
//        System.out.println("Consuming deadletter topic with original topic :::: " + orgTopic);
//        System.out.println("Consuming deadletter topic with original offset :::: " + orgOffset);
//
//        studentRecord.headers().forEach(header -> {
//
//            if (header.key().equals("kafka_dlt-original-offset")){
//                System.out.println(" kafka_dlt-original-offset header ::: " + header.key() + " the value is :::: " + ByteBuffer.wrap(header.value()).getLong());
//            }
//            System.out.println(" The key of header ::: " + header.key() + " the value is :::: " + new String(header.value()));
//        });
//
//
//        System.out.println(studentRecord);
//
//    }

    //============================================== Using record filtering strategy container ==========================================//

//    @KafkaListener(topics = {"MY_TOPIC"}, groupId = "MY_INTERCEPTOR_FILTERING_GROUP", containerFactory = "studentKafkaListenerContainerFactory5")
//    public void consume7(ConsumerRecord<String, Student> studentRecord) {
//        System.out.println("Consuming with listener and retry container factory for deadletter example");
//        System.out.println(studentRecord);
//    }
}
