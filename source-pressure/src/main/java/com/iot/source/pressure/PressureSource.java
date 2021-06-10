package com.iot.source.pressure;

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
public class PressureSource {

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
        taskScheduler.schedule(new Runnable() {
            @Override
            public void run() {
                MetricValue metricValue = MetricValue.builder()
                        .correlationId(UUID.randomUUID().toString())
                        .sourceId("pressure-sensor")
                        .metricType("pressure")
                        .metricName("pressure-1")
                        .flow(defaultFlow)
                        .build();
                metricValue.setValue(new Random().nextDouble());
                String firstTopic = metricValue.firstFlowItem();
                log.info("Sending message to topic {}", firstTopic);
                kafkaTemplate.send(firstTopic, metricValue);
            }
        }, new CronTrigger(sourceCron));
    }

}
