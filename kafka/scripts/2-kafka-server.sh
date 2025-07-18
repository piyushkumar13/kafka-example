$KAFKA_HOME/bin/kafka-server-start.sh $KAFKA_HOME/config/server.properties
#JMX_PORT=8004 $KAFKA_HOME/bin/kafka-server-start.sh $KAFKA_HOME/config/server.properties # If you want to run CMAK kafka manager, you need to set JMX port.


##### Starting kafka at localhost ip 127.0.0.1 ######

## There is a common issue when running Kafka locally, especially if your machine has multiple network interfaces
## or if your Docker setup (if you're using Docker) isn't configured for host networking.
## Kafka, by default, tries to bind to an available IP address on your machine, which might not always be localhost (127.0.0.1)
## or an IP address that other services can easily reach as localhost.
##
## When you start your Kafka broker, you might see a log message like this:
## Registered broker 0 at path /brokers/ids/0 with addresses: PLAINTEXT://192.168.0.167:9092
##
## The log message Registered broker 0 at path /brokers/ids/0 with addresses: PLAINTEXT://192.168.0.167:9092 indicates
## that your Kafka broker advertised itself using the IP address 192.168.0.167. This is the IP address that
## other Kafka brokers (if you had a cluster) and Kafka clients would try to use to connect to your broker.
##
##
## Here's how to fix it so your Kafka broker is accessible at localhost:
##
## You need to explicitly configure Kafka to advertise itself with the localhost IP address.
## This is done by modifying your server.properties file (or broker.properties, depending on your Kafka distribution).
##
## Steps:
##
## Locate server.properties:
## Edit server.properties. You'll need to add or modify the following properties:
## # listeners=PLAINTEXT://localhost:9092
## # advertised.listeners=PLAINTEXT://localhost:9092
## Save the changes to server.properties and then restart your Kafka broker.
##
## What does these properties mean?
## listeners: This property tells Kafka what interfaces to bind to for listening for client connections.
## Setting it to localhost:9092 makes it listen only on the loopback interface.
##
## advertised.listeners: This is the crucial one. It tells other brokers and clients how to connect to this broker.
## Even if the broker binds to 0.0.0.0 (all interfaces), if advertised.listeners is set to localhost, clients will be told to connect to localhost.
##
## Explanation:
## By setting advertised.listeners=PLAINTEXT://localhost:9092, you are telling Kafka to broadcast localhost:9092 as its connection endpoint.
## This means that when a client (like a producer or consumer) asks ZooKeeper (or Kafka itself, in newer versions without ZooKeeper)
## for the broker's address, it will receive localhost:9092.