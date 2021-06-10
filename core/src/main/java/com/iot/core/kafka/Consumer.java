package com.iot.core.kafka;

import com.iot.core.model.MetricValue;
import java.util.HashMap;
import java.util.Map;
import lombok.AllArgsConstructor;
import org.apache.kafka.clients.consumer.ConsumerConfig;
import org.apache.kafka.common.serialization.StringDeserializer;
import org.springframework.kafka.config.ConcurrentKafkaListenerContainerFactory;
import org.springframework.kafka.core.ConsumerFactory;
import org.springframework.kafka.core.DefaultKafkaConsumerFactory;

@AllArgsConstructor
public class Consumer {

    private final String broker;
    private final String groupId;

    public ConsumerFactory<String, MetricValue> consumerFactory() {
        return new DefaultKafkaConsumerFactory<>(consumerConfigurations());
    }

    public Map<String, Object> consumerConfigurations() {
        Map<String, Object> configurations = new HashMap<>();

        configurations.put(ConsumerConfig.BOOTSTRAP_SERVERS_CONFIG, broker);
        configurations.put(ConsumerConfig.GROUP_ID_CONFIG, groupId);
        configurations.put(ConsumerConfig.KEY_DESERIALIZER_CLASS_CONFIG, StringDeserializer.class);
        configurations.put(ConsumerConfig.VALUE_DESERIALIZER_CLASS_CONFIG,
                org.springframework.kafka.support.serializer.JsonDeserializer.class);
        configurations.put("spring.json.trusted.packages", "*");
        configurations.put("spring.kafka.consumer.properties.spring.json.trusted.packages", "*");

        return configurations;
    }

    public ConcurrentKafkaListenerContainerFactory<String, MetricValue> kafkaListenerContainerFactory() {
        ConcurrentKafkaListenerContainerFactory<String, MetricValue> factory = new ConcurrentKafkaListenerContainerFactory<>();
        factory.setConsumerFactory(consumerFactory());
        return factory;
    }
}
