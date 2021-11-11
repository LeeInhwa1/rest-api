package com.example.employee.management;

import com.example.employee.management.test.dao.maria.MariaUserMapper;
import com.example.employee.management.test.dao.mysql.MysqlUserMapper;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.EnableAspectJAutoProxy;

// AOP 설정을 위한 어노테이션
@EnableAspectJAutoProxy
@SpringBootApplication
@Slf4j
public class SpringRestApiApplication implements CommandLineRunner {

    @Autowired
    private MysqlUserMapper mysqlUserMapper;

    @Autowired
    private MariaUserMapper mariaUserMapper;


    public static void main(String args[]) {

        SpringApplication.run(SpringRestApiApplication.class, args);

        log.error("logging start error");
        log.warn("logging start warn");
        log.info("logging start info");
        log.trace("logging start trace");
        log.debug("logging start debug");
    }

    @Override
    public void run(String... args) throws Exception {
        try {
            log.info("MySQL result -> {}", this.mysqlUserMapper.getUserById(Long.parseLong("1")));
            log.info("MariaDB result -> {}", this.mariaUserMapper.getUserById(Long.parseLong("1")));
        }catch (Exception e) {
            System.out.println("error --->>>> " + e);
        }
    }
}
