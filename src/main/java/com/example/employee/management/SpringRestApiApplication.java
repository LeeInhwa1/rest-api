package com.example.employee.management;

import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.EnableAspectJAutoProxy;

// AOP 설정을 위한 어노테이션
@EnableAspectJAutoProxy
@SpringBootApplication
@Slf4j
public class SpringRestApiApplication {
    public static void main(String args[]) {
        SpringApplication.run(SpringRestApiApplication.class, args);

        log.error("logging start error");
        log.warn("logging start warn");
        log.info("logging start info");
        log.trace("logging start trace");
        log.debug("logging start debug");
    }
}
