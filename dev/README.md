# Dev environment setup
To start dev environment is required to have up and running Kafka and Influx db

## Kafka
 
```
cd kafka
docker-compose up
```

Kafka commands:

Create topic
```
kafka-topics --create --zookeeper zookeeper:2181 --replication-factor 1 --partitions 1   --topic source-pressure
kafka-topics --create --zookeeper zookeeper:2181 --replication-factor 1 --partitions 1   --topic transform-kelvin-to-celsius
kafka-topics --create --zookeeper zookeeper:2181 --replication-factor 1 --partitions 1   --topic sink-db
```

Start test consumer:
```
kafka-console-consumer --bootstrap-server localhost:9092 --topic source-pressure
```

## Influx db

```
cd influx
docker-compose up
```

Access influx WebUI:
```
http://localhost:8086
```

Initial setup:
```
      username: user
      password: password
      organization: org
      bucket: bucket
```