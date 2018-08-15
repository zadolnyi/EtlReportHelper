package com.oboz.reporthelper;

import com.zaxxer.hikari.HikariDataSource;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.autoconfigure.jdbc.DataSourceProperties;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.jdbc.core.JdbcTemplate;

import javax.sql.DataSource;

@Configuration
public class JdbcConfig {

    @Bean
    @ConfigurationProperties("source.datasource")
    public DataSourceProperties sourceDataSourceProperties() {
        return new DataSourceProperties();
    }

    @Bean (name = "sourceDB")
    @ConfigurationProperties("source.datasource")
    public DataSource sourceDataSource() {
        return sourceDataSourceProperties().initializeDataSourceBuilder().build();
    }

//    @Bean (name = "sourceDB")
//    @ConfigurationProperties("source.datasource")
//    public HikariDataSource sourceDB(DataSourceProperties properties) {
//        return properties.initializeDataSourceBuilder().type(HikariDataSource.class).build();
//    }

    @Bean(name = "sourceJdbcTemplate")
    public JdbcTemplate sourceJdbcTemplate(@Qualifier("sourceDB") DataSource ds) {
        return new JdbcTemplate(ds);
    }

    @Bean
    @ConfigurationProperties("target.datasource")
    public DataSourceProperties targetDataSourceProperties() {
        return new DataSourceProperties();
    }

    @Bean (name = "targetDB")
    @ConfigurationProperties("target.datasource")
    public DataSource targetDataSource() {
        return targetDataSourceProperties().initializeDataSourceBuilder().build();
    }

//    @Bean (name = "targetDB")
//    @ConfigurationProperties("target.datasource")
//    public HikariDataSource targetDB(DataSourceProperties properties) {
//        return properties.initializeDataSourceBuilder().type(HikariDataSource.class).build();
//    }

    @Bean(name = "targetJdbcTemplate")
    public JdbcTemplate targetJdbcTemplate(@Qualifier("targetDB") DataSource ds) {
        return new JdbcTemplate(ds);
    }
}
