package com.iot.access.service;

import com.influxdb.client.InfluxDBClient;
import com.influxdb.client.QueryApi;
import com.influxdb.query.FluxTable;
import com.iot.access.model.AggregationFunction;
import com.iot.core.influx.InfluxDbProvider;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

/**
 * Service to query metrics from InfluxDB
 */
@Service
public class MetricsQueryService {

    @Autowired
    private InfluxDbProvider influxDbProvider;

    @Value("${influx.bucket}")
    private String bucket;

    @Value("${influx.org}")
    private String org;

    public Double max(String timeInterval, String sourceName) {
        // Java 15 with multi line strings will be helpful here...
        String query = String.format("from(bucket: \"%s\")\n"
                + "  |> range(start:-%s)\n"
                + "  |> filter(fn: (r) => r[\"_measurement\"] == \"source=%s\")\n"
                + "  |> max()", bucket, timeInterval, sourceName);
        try (InfluxDBClient client = influxDbProvider.influxDBClient()) {
            QueryApi queryApi = client.getQueryApi();
            List<FluxTable> tables = queryApi.query(query, org);
            return Double.parseDouble(tables.get(0).getRecords().get(0).getValue().toString());
        }
    }

    public Double min(String timeInterval, String sourceName) {
        String query = String.format("from(bucket: \"%s\")\n"
                + "  |> range(start:-%s)\n"
                + "  |> filter(fn: (r) => r[\"_measurement\"] == \"source=%s\")\n"
                + "  |> min()", bucket, timeInterval, sourceName);
        try (InfluxDBClient client = influxDbProvider.influxDBClient()) {
            QueryApi queryApi = client.getQueryApi();
            List<FluxTable> tables = queryApi.query(query, org);
            return Double.parseDouble(tables.get(0).getRecords().get(0).getValue().toString());
        }
    }

    public Double mean(String timeInterval, String sourceName) {
        String query = String.format("from(bucket: \"%s\")\n"
                + "  |> range(start:-%s)\n"
                + "  |> filter(fn: (r) => r[\"_measurement\"] == \"source=%s\")\n"
                + "  |> mean()", bucket, timeInterval, sourceName);
        try (InfluxDBClient client = influxDbProvider.influxDBClient()) {
            QueryApi queryApi = client.getQueryApi();
            List<FluxTable> tables = queryApi.query(query, org);
            return Double.parseDouble(tables.get(0).getRecords().get(0).getValue().toString());
        }
    }

    public Double median(String timeInterval, String sourceName) {
        String query = String.format("from(bucket: \"%s\")\n"
                + "  |> range(start:-%s)\n"
                + "  |> filter(fn: (r) => r[\"_measurement\"] == \"source=%s\")\n"
                + "  |> median()", bucket, timeInterval, sourceName);
        try (InfluxDBClient client = influxDbProvider.influxDBClient()) {
            QueryApi queryApi = client.getQueryApi();
            List<FluxTable> tables = queryApi.query(query, org);
            return Double.parseDouble(tables.get(0).getRecords().get(0).getValue().toString());
        }
    }

    public Double runAggregation(AggregationFunction aggregation, String timeInterval, String sourceName) {
        switch (aggregation) {
            case MIN:
                return min(timeInterval, sourceName);
            case MAX:
                return max(timeInterval, sourceName);
            case MEAN:
                return mean(timeInterval, sourceName);
            case MEDIAN:
                return median(timeInterval, sourceName);
        }
        throw new RuntimeException("Not supported");
    }

}
