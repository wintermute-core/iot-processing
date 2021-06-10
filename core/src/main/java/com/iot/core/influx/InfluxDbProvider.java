package com.iot.core.influx;

import com.influxdb.client.InfluxDBClient;
import com.influxdb.client.InfluxDBClientFactory;
import lombok.AllArgsConstructor;

@AllArgsConstructor
public class InfluxDbProvider {

    private final String host;

    private final String token;

    public InfluxDBClient influxDBClient() {
        return InfluxDBClientFactory.create(host, token.toCharArray());
    }

}
