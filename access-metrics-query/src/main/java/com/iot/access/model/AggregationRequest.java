package com.iot.access.model;

import java.util.Collection;
import java.util.Map;
import lombok.Data;

/**
 * Request to aggregate multiple sensors data { metric_name: [ { MAX, 1h}, {MIN, 1h} } }
 */
@Data
public class AggregationRequest {

    private Map<String, Collection<AggregationOption<AggregationFunction, String>>> metrics;

}
