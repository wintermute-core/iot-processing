package com.iot.sinkdb;

import com.iot.sinkdb.service.InfluxDbProvider;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

@SpringBootApplication
public class DbSinkApplication {


    @Bean
    public InfluxDbProvider influxDbProvider(@Value("${influx.host}") String host,
            @Value("${influx.token}") String token) {
        return new InfluxDbProvider(host, token);
    }

    public static void main(String[] args) throws InterruptedException {
        SpringApplication.run(DbSinkApplication.class, args);
//
//
//		// You can generate a Token from the "Tokens Tab" in the UI
//		String token = "jIfwboT-FAUbhln33zTyRt0AjTvGtE9JNxfMQ7fSX7mK5SR-wufTbChhyt1CbZ64uaxRCXhwvqi231hXOZQrSA==";
//		String bucket = "bucket";
//		String org = "user";
//
//		InfluxDBClient client = InfluxDBClientFactory.create("http://localhost:8086", token.toCharArray());
////
////		for(int i =0 ;i<100;i++) {
////			String data = "mem,host=host1 used_percent=" + (10 + new Random().nextInt(5));
////			try (WriteApi writeApi = client.getWriteApi()) {
////				writeApi.writeRecord(bucket, org, WritePrecision.NS, data);
////			}
////			Thread.sleep(1000);
////
////		}
//
//		String query = String.format("from(bucket: \"%s\") |> range(start: -1h)", bucket);
//		List<FluxTable> tables = client.getQueryApi().query(query, org);
//		for(FluxTable fluxTable : tables) {
//			System.out.println(fluxTable.getColumns() + "");
//
//		}
    }

}
