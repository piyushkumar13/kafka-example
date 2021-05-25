package com.piyush;

/**
 * @author Piyush Kumar.
 * @since 25/05/21.
 */
public interface AppConfig {

    String APPLICATION_ID = "MY_ID";
    String BOOTSTRAP_SERVER = "localhost:9092";
    String TOPIC_NAME = "MY_TOPIC";
    int numEvents = 50;

    String GROUP_ID = "MY_GROUP";


}
