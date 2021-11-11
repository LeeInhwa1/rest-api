package com.example.employee.management.test.config;

import com.zaxxer.hikari.HikariConfig;
import com.zaxxer.hikari.HikariDataSource;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;

import javax.sql.DataSource;

@Configuration
public class DataSourceConfig {

    @Bean
    @Qualifier("primaryHikariConfig")
    @ConfigurationProperties(prefix="spring.datasource.hikari.primary")
    public HikariConfig hikariConfig() {
        return new HikariConfig();
    }

    @Bean
    @Primary
    @Qualifier("primaryDataSource")
    public DataSource dataSource() throws Exception {
        return new HikariDataSource(hikariConfig());
    }

    @Bean
    @Qualifier("secondaryHikariConfig")
    @ConfigurationProperties(prefix="spring.datasource.hikari.secondary")
    public HikariConfig secondaryHikariConfig() {
        return new HikariConfig();
    }

    @Bean
    @Qualifier("secondaryDataSource")
    public DataSource secondaryDataSource() throws Exception {
        return new HikariDataSource(secondaryHikariConfig());
    }
}
