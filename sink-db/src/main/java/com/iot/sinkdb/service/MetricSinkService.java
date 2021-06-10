package com.iot.sinkdb.service;

import com.influxdb.client.InfluxDBClient;
import com.influxdb.client.WriteApi;
import com.influxdb.client.domain.WritePrecision;
import com.iot.core.model.MetricValue;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

@Service
@Slf4j
public class MetricSinkService {

    private static final String VALUE_KEY = "value";

    @Autowired
    private InfluxDbProvider influxDbProvider;

    @Value("${influx.bucket}")
    private String metricBucket;
    @Value("${influx.org}")
    private String metricOrg;

    public void sink(MetricValue metricValue) {
        if (!metricValue.getPayload().containsKey(VALUE_KEY)) {
            log.warn("No value to persist in metric from {} {}", metricValue.getSourceId(), metricValue);
            return;
        }
        try (InfluxDBClient client = influxDbProvider.influxDBClient()) {
            String value = String.valueOf(metricValue.getPayload().get(VALUE_KEY));
            String data = String
                    .format("source=%s %s=%s", metricValue.getSourceId(), metricValue.getMetricName(), value);
            try (WriteApi writeApi = client.getWriteApi()) {
                writeApi.writeRecord(metricBucket, metricOrg, WritePrecision.NS, data);
            }
        }
    }

}
