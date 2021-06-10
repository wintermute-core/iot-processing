package com.iot.sinkdb;

import com.iot.core.influx.InfluxDbProvider;
import com.iot.core.kafka.Consumer;
import com.iot.core.model.MetricValue;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.kafka.config.ConcurrentKafkaListenerContainerFactory;

@SpringBootApplication
public class DbSinkApplication {

    @Bean
    public Consumer consumer(@Value("${kafka.broker}") String broker, @Value("${kafka.group}") String groupId) {
        return new Consumer(broker, groupId);
    }

    @Bean
    public ConcurrentKafkaListenerContainerFactory<String, MetricValue> kafkaListenerContainerFactory(Consumer consumer) {
        return consumer.kafkaListenerContainerFactory();
    }

    @Bean
    public InfluxDbProvider influxDbProvider(@Value("${influx.host}") String host,
            @Value("${influx.token}") String token) {
        return new InfluxDbProvider(host, token);
    }

    public static void main(String[] args) throws InterruptedException {
        SpringApplication.run(DbSinkApplication.class, args);
    }

}
