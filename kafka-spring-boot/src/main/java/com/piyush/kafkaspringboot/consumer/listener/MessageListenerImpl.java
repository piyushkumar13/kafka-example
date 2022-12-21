/*
 *  Copyright (c) 2022 LogMeIn
 *  All Rights Reserved Worldwide.
 *
 *  THIS PROGRAM IS CONFIDENTIAL AND PROPRIETARY TO LOGMEIN
 *  AND CONSTITUTES A VALUABLE TRADE SECRET.
 */
package com.piyush.kafkaspringboot.consumer.listener;

import com.piyush.kafkaspringboot.domain.Student;
import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.springframework.kafka.listener.MessageListener;

/**
 * @author Piyush Kumar.
 * @since 21/12/22.
 */
public class MessageListenerImpl implements MessageListener<String, Student> {


    @Override
    public void onMessage(ConsumerRecord<String, Student> data) {

        System.out.println("The consumer record is received :::: " + data);

        System.out.println("The data is ::: " + data.value());

        data.headers().forEach(header -> System.out.println("The key is ::: " + header.key() + " the value is :::: " + new String(header.value())));

    }
}
