$KAFKA_HOME/bin/kafka-console-consumer.sh --bootstrap-server localhost:9092 --topic MY_TOPIC --from-beginning  --group MY_GROUP # creates consumer which belongs to a group. You can create multiple consumer which can belong to this same group.