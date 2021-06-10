package com.iot.transform;

import com.iot.core.model.MetricValue;
import java.util.Optional;
import java.util.UUID;
import lombok.Getter;
import lombok.Setter;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Component;

/**
 * Transform Kelvin temperature to Celsius and pass message to next item in flow.
 */
@Component
@Slf4j
public class KelvinTransformListener {

    private static final double CELSIUS_TRANSFORM_CONSTANT = -273.15d;

    private static final String ORIGINAL_VALUE = "kelvin_original_value";

    @Autowired
    private KafkaTemplate<String, MetricValue> kafkaTemplate;

    @Value("${transform.topic}")
    @Getter
    @Setter
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
            metricValue.getPayload().put(ORIGINAL_VALUE, kelvinValue); // saving original value just for reference
            metricValue.setValue(kelvinValue + CELSIUS_TRANSFORM_CONSTANT);
            Optional<String> nextItem = metricValue.nextItem(listenTopic);
            if (nextItem.isEmpty()) {
                log.warn("No next topic where to send message {}, dropping", metricValue.getCorrelationId());
                return;
            }
            kafkaTemplate.send(nextItem.get(), UUID.randomUUID().toString(), metricValue);
        } catch (Exception e) {
            log.error("Failed to process metric value {}", metricValue, e);
        }
    }

}
