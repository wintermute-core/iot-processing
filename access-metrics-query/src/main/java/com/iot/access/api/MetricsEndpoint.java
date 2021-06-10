package com.iot.access.api;

import com.iot.access.model.AggregationFunction;
import com.iot.access.model.AggregationRequest;
import com.iot.access.model.AggregationResponse;
import com.iot.access.service.MetricsQueryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class MetricsEndpoint {

    @Autowired
    private MetricsQueryService metricsQueryService;

    @GetMapping("/metric/{metric}/interval/{interval}")
    public AggregationResponse singleMetric(@PathVariable("metric") String metric,
            @PathVariable("interval") String interval) {
        AggregationResponse aggregationResponse = new AggregationResponse();
        aggregationResponse.addPair(metric, AggregationFunction.MAX, metricsQueryService.max(interval, metric));
        aggregationResponse.addPair(metric, AggregationFunction.MIN, metricsQueryService.min(interval, metric));
        aggregationResponse.addPair(metric, AggregationFunction.MEAN, metricsQueryService.mean(interval, metric));
        aggregationResponse.addPair(metric, AggregationFunction.MEDIAN, metricsQueryService.median(interval, metric));
        return aggregationResponse;
    }

    @PostMapping("/query")
    public AggregationResponse query(@RequestBody AggregationRequest aggregationRequest) {
        AggregationResponse aggregationResponse = new AggregationResponse();

        aggregationRequest.getMetrics().entrySet().forEach(item -> {
            item.getValue().forEach(value -> {
                aggregationResponse.addPair(item.getKey(), value.getFunction(), metricsQueryService.runAggregation(value.getFunction(), value.getInterval(), item.getKey()));
            });
        });


        return aggregationResponse;
    }


}
