package com.iot.sinkdb.service;

import com.influxdb.client.InfluxDBClient;
import com.influxdb.client.InfluxDBClientFactory;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

@AllArgsConstructor
public class InfluxDbProvider {

    private final String host;

    private final String token;

    public InfluxDBClient influxDBClient() {
        return InfluxDBClientFactory.create(host, token.toCharArray());
    }

}
