package com.iot.core.model;

import java.util.List;
import java.util.Map;
import java.util.Optional;
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

    public String firstFlowItem() {
        return flow.get(0);
    }

    public String lastFlowItem() {
        return flow.get(flow.size() - 1);
    }

    public Optional<String> nextItem(String currentItem) {
        for (int i = 0; i < flow.size(); i++) {
            if (currentItem.equals(flow.get(i)) && i != flow.size() - 1) {
                return Optional.of(flow.get(i + 1));
            }
        }
        return Optional.empty();
    }

}
