package com.sdcuike.springboot;

import com.sdcuike.extend.dynamic.datasource.annotation.EnableDynamicDatasource;
import com.sdcuike.extend.mvc.jackson.annotation.EnableJacksonExtendAutoConfigure;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.jdbc.DataSourceAutoConfiguration;
import org.springframework.context.annotation.EnableAspectJAutoProxy;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;


@SpringBootApplication(exclude = {DataSourceAutoConfiguration.class})
@EnableScheduling
@EnableDynamicDatasource
@EnableJacksonExtendAutoConfigure
@EnableAspectJAutoProxy(proxyTargetClass = true)
@Slf4j
public class SpringBootPracticeApplication {

    public static void main(String[] args) {
        SpringApplication.run(SpringBootPracticeApplication.class, args);
    }


    @Scheduled(fixedDelay = 1000)
    public void testScheduled() {
        log.info("testScheduled");
    }
}
