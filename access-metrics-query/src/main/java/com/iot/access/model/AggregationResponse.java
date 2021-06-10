package com.iot.access.model;

import java.util.HashMap;
import java.util.Map;
import lombok.Data;


/**
 * Aggregation response
 */
@Data
public class AggregationResponse {

    private Map<String, Map<AggregationFunction, Object>> metrics = new HashMap<>();

    public void addPair(String metric, AggregationFunction function, Object value) {
        if (!metrics.containsKey(metric)) {
            metrics.put(metric, new HashMap<>());
        }
        metrics.get(metric).put(function, value);
    }

}
