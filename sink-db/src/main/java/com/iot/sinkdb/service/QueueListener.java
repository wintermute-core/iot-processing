package com.iot.sinkdb.service;

import com.iot.core.model.MetricValue;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Component;

@Component
@Slf4j
public class QueueListener {

    @Autowired
    private MetricSinkService metricSinkService;

    @KafkaListener(topics = "${sink.topic}", groupId = "${kafka.group}")
    public void listen(MetricValue metricValue) {
        log.info("Handling message {} of type {}", metricValue.getCorrelationId(), metricValue.getMetricType());
        if (metricValue.readValue().isEmpty()) {
            log.warn("No value to persist in metric from {} {}", metricValue.getSourceId(), metricValue);
            return;
        }
        try {
            metricSinkService.sink(metricValue);
        } catch (Exception e) {
            log.error("Failed to process metric value {}", metricValue, e);
        }
    }

}
