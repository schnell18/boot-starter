package org.home.hone.influx;

import org.influxdb.dto.Point;
import org.influxdb.dto.Query;
import org.influxdb.dto.QueryResult;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.influxdb.InfluxDBTemplate;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import javax.annotation.Resource;
import java.util.concurrent.TimeUnit;

@Component
@SpringBootApplication
public class InfluxStarter {

    @Resource
    private InfluxDBTemplate<Point> influxDBTemplate;

    public void sendData() {
        String[] tenants = {"apple", "tencent", "lenovo"};
        for (int i = 0; i < 1000; i ++) {
            long used = 80L + i % 10;
            final Point p = Point.measurement("disk")
                    .time(System.currentTimeMillis(), TimeUnit.MILLISECONDS)
                    .tag("tenant", tenants[i % tenants.length])
                    .addField("used", used)
                    .addField("free", 100L - used)
                    .build();
            influxDBTemplate.write(p);
        }
    }

    public void queryData() {
        String db = influxDBTemplate.getDatabase();
        Query query = new Query("SELECT used, free FROM disk where tenant = 'default'", db);
        QueryResult result = influxDBTemplate.query(query);
        System.out.printf("query result=%s\n", result);
    }

    @PostConstruct
    public void init() {
       sendData();
       queryData();
    }

    public static void main(String[] args) {
        SpringApplication.run(InfluxStarter.class, args);
    }
}
