package com.iot;

import com.influxdb.client.InfluxDBClient;
import com.influxdb.client.InfluxDBClientFactory;
import com.influxdb.query.FluxTable;
import java.util.List;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class DbSinkApplication {

	public static void main(String[] args) throws InterruptedException {
		//SpringApplication.run(InfluxqueryApplication.class, args);


		// You can generate a Token from the "Tokens Tab" in the UI
		String token = "jIfwboT-FAUbhln33zTyRt0AjTvGtE9JNxfMQ7fSX7mK5SR-wufTbChhyt1CbZ64uaxRCXhwvqi231hXOZQrSA==";
		String bucket = "bucket";
		String org = "user";

		InfluxDBClient client = InfluxDBClientFactory.create("http://localhost:8086", token.toCharArray());
//
//		for(int i =0 ;i<100;i++) {
//			String data = "mem,host=host1 used_percent=" + (10 + new Random().nextInt(5));
//			try (WriteApi writeApi = client.getWriteApi()) {
//				writeApi.writeRecord(bucket, org, WritePrecision.NS, data);
//			}
//			Thread.sleep(1000);
//
//		}

		String query = String.format("from(bucket: \"%s\") |> range(start: -1h)", bucket);
		List<FluxTable> tables = client.getQueryApi().query(query, org);
		for(FluxTable fluxTable : tables) {
			System.out.println(fluxTable.getColumns() + "");

		}
	}

}
