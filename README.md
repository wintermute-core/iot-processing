# IoT data processing pipeline

## Motivation

Pipeline to ingest and query data from IoT devices

## Stack

Project tech stack:
  * Java 11 
  * Spring boot
  * Lombok
  * Kafka
  * Influx
  * Docker
  * Kubernetes
  * Docker compose

## Metrics query

http://localhost:9777/swagger-ui.html

```

curl -v localhost:9777/interval/1h/metric/pressure-sensor

{
  "min": 0.009788386689890127,
  "median": 0.47214310746901345,
  "max": 0.9972137758382438,
  "mean": 0.4767313862774201
}

```

# License

Only for reference, distribution and/or commercial usage not allowed