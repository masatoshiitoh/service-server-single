#!/bin/bash

cd $HOME/bin/kafka_2.13-3.5.0
bin/zookeeper-server-start.sh config/zookeeper.properties &
sleep 30
bin/kafka-server-start.sh config/server.properties &

