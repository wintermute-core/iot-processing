package com.iot.core.model;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Builder.Default;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * Data class which represents one metric collected from IoT device
 */
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class MetricValue {

    public static final String VALUE_KEY = "value";

    // version of value
    @Default
    private String version = "1.0";

    // unique identifier of this value
    private String correlationId;

    private String sourceId;

    private String metricType;
    private String metricName;

    @Default
    private Map<String, Object> payload = new HashMap<>();

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

    public Optional<Object> readValue() {
        return Optional.ofNullable(payload.get(VALUE_KEY));
    }
    public void setValue(Object value) {
        payload.put(VALUE_KEY, value);
    }

}
