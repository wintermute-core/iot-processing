package com.iot.source.pressure;

import com.iot.core.kafka.Consumer;
import com.iot.core.kafka.Producer;
import com.iot.core.model.MetricValue;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.kafka.config.ConcurrentKafkaListenerContainerFactory;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.scheduling.concurrent.ThreadPoolTaskScheduler;


@SpringBootApplication
public class KelvinToCelsiusApplication {

    @Bean
    public Producer kafkaProducer(@Value("${kafka.broker}") String broker) {
        return new Producer(broker);
    }

    @Bean
    public KafkaTemplate<String, MetricValue> kafkaTemplate(Producer producer) {
        return producer.kafkaTemplate();
    }

    @Bean
    public Consumer consumer(@Value("${kafka.broker}") String broker, @Value("${kafka.group}") String groupId) {
        return new Consumer(broker, groupId);
    }

    @Bean
    public ConcurrentKafkaListenerContainerFactory<String, MetricValue> kafkaListenerContainerFactory(Consumer consumer) {
        return consumer.kafkaListenerContainerFactory();
    }

    @Bean
    public ThreadPoolTaskScheduler threadPoolTaskScheduler(){
        ThreadPoolTaskScheduler threadPoolTaskScheduler
                = new ThreadPoolTaskScheduler();
        threadPoolTaskScheduler.setPoolSize(1);
        threadPoolTaskScheduler.setThreadNamePrefix(
                "kelvin-temperature");
        return threadPoolTaskScheduler;
    }

    public static void main(String[] args) throws InterruptedException {
        SpringApplication.run(KelvinToCelsiusApplication.class, args);
    }

}
