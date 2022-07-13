#! /bin/bash

# This script only works for mac installations
#make sure all the 3 brokers are up and running before executing this script

kafka-topics --bootstrap-server localhost:9092 --create --topic market-place-events --partitions 3 --replication-factor 3
kafka-configs --bootstrap-server localhost:9092 --alter --entity-type topics --entity-name market-place-events --add-config min.insync.replicas=3
kafka-topics --describe --bootstrap-server localhost:9092 --topic market-place-events
