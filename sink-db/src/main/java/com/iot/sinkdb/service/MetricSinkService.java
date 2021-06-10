package com.iot.sinkdb.service;


import com.influxdb.client.InfluxDBClient;
import com.influxdb.client.WriteApi;
import com.influxdb.client.domain.WritePrecision;
import com.iot.core.influx.InfluxDbProvider;
import com.iot.core.model.MetricValue;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

@Service
@Slf4j
public class MetricSinkService {
    @Autowired
    private InfluxDbProvider influxDbProvider;

    @Value("${influx.bucket}")
    private String metricBucket;

    @Value("${influx.org}")
    private String metricOrg;

    public void sink(MetricValue metricValue) {
        try (InfluxDBClient client = influxDbProvider.influxDBClient()) {
            String value = String.valueOf(metricValue.readValue().get());
            String data = String
                    .format("source=%s %s=%s", metricValue.getSourceId(), metricValue.getMetricName(), value);
            try (WriteApi writeApi = client.getWriteApi()) {
                writeApi.writeRecord(metricBucket, metricOrg, WritePrecision.NS, data);
            }
        }
    }

}
