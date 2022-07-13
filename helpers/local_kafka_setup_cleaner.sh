#! /bin/bash

# This script only works for mac installations

kafka-server-stop /usr/local/etc/kafka/kraft/server1.properties
kafka-server-stop /usr/local/etc/kafka/kraft/server2.properties
kafka-server-stop /usr/local/etc/kafka/kraft/server3.properties

rm -rf /tmp/server1/kraft-combined-logs
rm -rf /tmp/server2/kraft-combined-logs
rm -rf /tmp/server3/kraft-combined-logs

RANDOM_UUID=$(kafka-storage random-uuid)
echo "Random uuid: $RANDOM_UUID"
kafka-storage format -t "$RANDOM_UUID" -c /usr/local/etc/kafka/kraft/server1.properties
kafka-storage format -t "$RANDOM_UUID" -c /usr/local/etc/kafka/kraft/server2.properties
kafka-storage format -t "$RANDOM_UUID" -c /usr/local/etc/kafka/kraft/server3.properties




