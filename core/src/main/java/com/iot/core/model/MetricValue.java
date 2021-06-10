package com.iot.core.model;

import java.util.List;
import java.util.Map;
import lombok.Data;

/**
 * Data class which represents one metric collected from IoT device
 */
@Data
public class MetricValue {

    // version of value
    private String version;

    // unique identifier of this value
    private String correlationId;

    private String sourceId;

    private String metricType;
    private String metricName;


    private Map<String, Object> payload;

    // list of processors/sink which this value should flow
    private List<String> flow;

}
