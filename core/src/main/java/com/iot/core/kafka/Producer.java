package com.iot.core.kafka;

import com.iot.core.model.MetricValue;
import java.util.HashMap;
import java.util.Map;
import lombok.AllArgsConstructor;
import org.apache.kafka.clients.producer.ProducerConfig;
import org.apache.kafka.common.serialization.StringSerializer;
import org.springframework.kafka.core.DefaultKafkaProducerFactory;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.kafka.core.ProducerFactory;
import org.springframework.kafka.support.serializer.JsonSerializer;

/**
 * Kafka producer configuration and access classes
 */
@AllArgsConstructor
public class Producer {

    private final String broker;

    public ProducerFactory<String, MetricValue> producerFactory() {
        return new DefaultKafkaProducerFactory<>(producerConfigurations());
    }

    public Map<String, Object> producerConfigurations() {
        Map<String, Object> configurations = new HashMap<>();

        configurations.put(ProducerConfig.BOOTSTRAP_SERVERS_CONFIG, broker);
        configurations.put(ProducerConfig.KEY_SERIALIZER_CLASS_CONFIG, StringSerializer.class);
        configurations.put(ProducerConfig.VALUE_SERIALIZER_CLASS_CONFIG, JsonSerializer.class);

        return configurations;
    }

    public KafkaTemplate<String, MetricValue> kafkaTemplate() {
        return new KafkaTemplate<>(producerFactory());
    }

}
