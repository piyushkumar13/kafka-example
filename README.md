# kafka-example

This project comprises of modules : 

1. kafka module
2. kafka-producer-consumer
3. kafka-manager


## kafka Module


It actually contains script to execute kafka commands to run zookeeper, kafka-server(broker), create kafka topic etc.

I have added kafka to this project, so that kafka setup could run multiple times without any problem, which you may need to run kafka multiple times for local debugging purposes from the scratch.
Before using it, you need to perform following steps : 

1. For this, download kafka binaries from [here](https://kafka.apache.org/downloads).
2. Unzip the downloaded zip file.
3. Open kafka folder and navigate to config folder.
4. Change server.properties `log.dirs` to point to value **`../tmp/kafka-logs`** and zookeeper.properties `dataDir` to point to value **`../tmp/zookeeper`**.
NOTE: if you notice here, we just added two dots `..` in front of the values. This represent your current working directory.
This helps to run kafka multiple times without any conflict. You can **always delete tmp folder** which would have been created in your current working directory when running kafka next time.
5. Set KAFKA_HOME env variable in .bash_profile which will point to the kafka folder which is extracted from the zip file(in step 1).
6. Execute 1.. to ..5 scripts in sequence.


## kafka-producer-consumer

This module comprises of producer and consumer scripts.
Execute producer script and provide values in the terminal.
Execute consumer script to consume values produced by producer.


## kafka-manager

This module contains the script to start [CMAK](https://github.com/yahoo/CMAK) (Cluster Manager for Apache Kafka).

* Please follow step as mentioned in [this](https://github.com/yahoo/CMAK#deployment) page or refer [this](https://www.youtube.com/watch?v=AlQfpG10vAc) video for demo.
* Please setup env variable `CMAK_HOME` which should point to cmak dist created by above step.
* Execute `start-kafka-manager.sh`