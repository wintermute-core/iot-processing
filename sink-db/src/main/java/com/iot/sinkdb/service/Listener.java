package com.iot.sinkdb.service;

import com.iot.core.model.MetricValue;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Component;

@Component
@Slf4j
public class Listener {

    @Autowired
    private MetricSinkService metricSinkService;

    @KafkaListener(topics = "${sink.topic}", groupId = "${kafka.group}")
    public void listen(MetricValue metricValue) {
        log.info("Handling message {} of type {}", metricValue.getCorrelationId(), metricValue.getMetricType());
        try {
            metricSinkService.sink(metricValue);
        } catch (Exception e) {
            log.error("Failed to process metric value {}", metricValue, e);
        }
    }

}
