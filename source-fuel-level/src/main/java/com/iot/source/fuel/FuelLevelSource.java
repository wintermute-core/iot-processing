package com.iot.source.fuel;

import com.iot.core.model.MetricValue;
import java.util.List;
import java.util.Random;
import java.util.UUID;
import javax.annotation.PostConstruct;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.scheduling.TaskScheduler;
import org.springframework.scheduling.support.CronTrigger;
import org.springframework.stereotype.Component;

@Component
@Slf4j
public class FuelLevelSource {

    @Autowired
    private TaskScheduler taskScheduler;

    @Autowired
    private KafkaTemplate<String, MetricValue> kafkaTemplate;

    @Value("${source.cron}")
    private String sourceCron;

    @Value("${source.flow}")
    private List<String> defaultFlow;

    @PostConstruct
    void init() {
        taskScheduler.schedule(() -> {
            MetricValue metricValue = MetricValue.builder()
                    .correlationId(UUID.randomUUID().toString())
                    .sourceId("fuel-level-sensor")
                    .metricType("fuel-level")
                    .metricName("fuel-level-1")
                    .flow(defaultFlow)
                    .build();
            // Fuel level, 0-100
            metricValue.setValue(new Random().nextInt(100));
            String firstTopic = metricValue.firstFlowItem();
            log.info("Sending message to topic {} {}", firstTopic, metricValue.getCorrelationId());
            kafkaTemplate.send(firstTopic, UUID.randomUUID().toString(), metricValue);
        }, new CronTrigger(sourceCron));
    }

}
