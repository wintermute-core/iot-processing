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

Query single metric:
```

curl -v localhost:9777/metric/pressure-sensor/interval/1h

{
  "min": 0.009788386689890127,
  "median": 0.47214310746901345,
  "max": 0.9972137758382438,
  "mean": 0.4767313862774201
}

```
Aggregation request:
```
curl -X POST "http://localhost:9777/query" -H  "accept: */*" -H  "Content-Type: application/json" -d "{\"metrics\":{\"pressure-sensor\":[{\"function\":\"MIN\",\"interval\":\"1h\"},{\"function\":\"MAX\",\"interval\":\"1h\"}],\"fuel-level-sensor\":[{\"function\":\"MEAN\",\"interval\":\"1h\"}],\"temperature-kelvin-sensor\":[{\"function\":\"MEAN\",\"interval\":\"2h\"}]}}"

Request:
{
  "metrics": {
    "pressure-sensor": [
      {
        "function": "MIN",
        "interval": "1h"
      },
      {
        "function": "MAX",
        "interval": "1h"
      }
    ],
    "fuel-level-sensor": [
      {
        "function": "MEAN",
        "interval": "1h"
      }
    ],
    "temperature-kelvin-sensor": [
      {
        "function": "MEAN",
        "interval": "2h"
      }
    ]
  }
}

Response:

{
  "metrics": {
    "pressure-sensor": {
      "MIN": 0.005507284385931288,
      "MAX": 0.9987821444161251
    },
    "temperature-kelvin-sensor": {
      "MEAN": 4215.17083792702
    },
    "fuel-level-sensor": {
      "MEAN": 50.61309523809524
    }
  }
}
```

# License

Only for reference, distribution and/or commercial usage not allowed