package com.iot.source.pressure;

import com.iot.core.model.MetricValue;
import java.util.Date;
import java.util.Optional;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Component;

@Component
@Slf4j
public class KelvinTransformListener {

    private static final double CELSIUS_TRANSFORM_CONSTANT = -273.15d;

    @Autowired
    private KafkaTemplate<String, MetricValue> kafkaTemplate;

    @Value("${transform.topic}")
    private String listenTopic;

    @KafkaListener(topics = "${transform.topic}", groupId = "${kafka.group}")
    public void listen(MetricValue metricValue) {
        log.info("Handling message {}", metricValue.getCorrelationId());
        try {
            if (metricValue.readValue().isEmpty()) {
                log.warn("No value in message {}, dropping", metricValue.getCorrelationId());
                return;
            }
            double kelvinValue = Double.parseDouble(metricValue.readValue().get().toString());
            metricValue.setValue(kelvinValue + CELSIUS_TRANSFORM_CONSTANT);
            Optional<String> nextItem = metricValue.nextItem(listenTopic);
            if (nextItem.isEmpty()) {
                log.warn("No next topic where to send message {}, dropping", metricValue.getCorrelationId());
                return;
            }
            kafkaTemplate.send(nextItem.get(), new Date().toString(), metricValue);
        } catch (Exception e) {
            log.error("Failed to process metric value {}", metricValue, e);
        }
    }

}
