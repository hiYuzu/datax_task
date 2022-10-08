package com.sinosoft.datax_task;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.jdbc.DataSourceAutoConfiguration;
import org.springframework.scheduling.annotation.EnableAsync;
import org.springframework.scheduling.annotation.EnableScheduling;

/**
 * @author hiYuzu
 * @version V1.0
 * @date 2022/6/25 10:35
 */
@SpringBootApplication(exclude = {DataSourceAutoConfiguration.class})
@EnableScheduling
@EnableAsync
public class DataxTaskApplication {
    public static void main(String[] args) {
        SpringApplication.run(DataxTaskApplication.class, args);
    }
}
