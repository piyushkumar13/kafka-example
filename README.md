# kafka-example
This project comprises of modules : 

* kafka module


##kafka Module

It actually contains script to execute kafka commands to run zookeeper, kafka-server(broker), create kafka topic etc.

I have added kafka to this project, so that kafka setup could run multiple times without any problem, which you may need to run kafka multiple times for local debugging purposes from the scratch.
Before using it, you need to perform following steps : 

1. For this, download kafka binaries from [here](https://kafka.apache.org/downloads).
2. Unzip the downloaded zip file.
3. Open kafka folder and navigate to config folder.
4. Change server.properties `log.dirs` to point to value *`../tmp/kafka-logs`* and zookeeper.properties `dataDir` to point to value `../tmp/zookeeper`.
NOTE: if you notice here, we just added two dots `..` in front of the values. This represent your current working directory.
This helps to run kafka multiple times without any conflict. You can always delete tmp folder which would have been created in your current working directory when running kafka next time.
5. Set KAFKA_HOME env variable in .bash_profile which will point to the kafka folder which is extracted from the zip file(in step 1).
6. Execute 1.. to ..5 scripts in sequence.
