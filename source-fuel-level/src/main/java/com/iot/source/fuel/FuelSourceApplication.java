package com.iot.source.fuel;

import com.iot.core.kafka.Producer;
import com.iot.core.model.MetricValue;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.scheduling.concurrent.ThreadPoolTaskScheduler;


@SpringBootApplication
public class FuelSourceApplication {

    @Bean
    public Producer kafkaProducer(@Value("${kafka.broker}") String broker) {
        return new Producer(broker);
    }

    @Bean
    public KafkaTemplate<String, MetricValue> kafkaTemplate(Producer producer) {
        return producer.kafkaTemplate();
    }

    @Bean
    public ThreadPoolTaskScheduler threadPoolTaskScheduler() {
        ThreadPoolTaskScheduler threadPoolTaskScheduler
                = new ThreadPoolTaskScheduler();
        threadPoolTaskScheduler.setPoolSize(1);
        threadPoolTaskScheduler.setThreadNamePrefix(
                "fuel-source");
        return threadPoolTaskScheduler;
    }

    public static void main(String[] args) throws InterruptedException {
        SpringApplication.run(FuelSourceApplication.class, args);
    }

}
