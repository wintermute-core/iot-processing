package com.iot.access;

import com.iot.core.influx.InfluxDbProvider;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

@SpringBootApplication
public class DbAccessApplication {

    @Bean
    public InfluxDbProvider influxDbProvider(@Value("${influx.host}") String host,
            @Value("${influx.token}") String token) {
        return new InfluxDbProvider(host, token);
    }

    public static void main(String[] args) throws InterruptedException {
        SpringApplication.run(DbAccessApplication.class, args);
    }

}
