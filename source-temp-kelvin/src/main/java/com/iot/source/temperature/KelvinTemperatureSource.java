package com.iot.source.temperature;

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
public class KelvinTemperatureSource {

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
                    .sourceId("temperature-kelvin-sensor")
                    .metricType("temperature")
                    .metricName("kelvin-temperature-1")
                    .flow(defaultFlow)
                    .build();
            // kelvin temperature 1000 - 10000
            metricValue.setValue(1 + 1000 * new Random().nextInt(10));
            String firstTopic = metricValue.firstFlowItem();
            log.info("Sending message to topic {} {}", firstTopic, metricValue.getCorrelationId());
            kafkaTemplate.send(firstTopic, UUID.randomUUID().toString(), metricValue);
        }, new CronTrigger(sourceCron));
    }

}
