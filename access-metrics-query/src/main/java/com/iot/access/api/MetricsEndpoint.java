package com.iot.access.api;

import com.iot.access.service.MetricsQueryService;
import java.util.HashMap;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class MetricsEndpoint {

    @Autowired
    private MetricsQueryService metricsQueryService;

    @GetMapping("/interval/{interval}/metric/{metric}")
    public HashMap produce(@PathVariable("metric") String metric, @PathVariable("interval") String interval) {
        HashMap<String, Object> value = new HashMap<>();

        value.put("max", metricsQueryService.max(interval, metric));
        value.put("min", metricsQueryService.min(interval, metric));
        value.put("mean", metricsQueryService.mean(interval, metric));
        value.put("median", metricsQueryService.median(interval, metric));

        return value;

    }
}
