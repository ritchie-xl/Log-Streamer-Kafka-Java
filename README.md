# Log-Streamer-Kafka-Java
Use kafka to stream log real time from 
a simple log4j log generate

# Prerequiste
A running kafka server base on a zookeeper cluster

# Log Generator
Use log4j to constructa simple log generate real timely and saved to the log file every 0.5 sec

# Log Streamer ( called producer in kafka)
Read the log file repeatly and once new logs have been added to the log file, 

start streaming the newly added logs to the kafka server so that the kafka consumer can 

consume the newly added logs
