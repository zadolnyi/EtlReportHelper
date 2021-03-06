package com.zadolnyi.reporthelper;

import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.autoconfigure.jdbc.DataSourceProperties;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.boot.orm.jpa.EntityManagerFactoryBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.orm.jpa.JpaTransactionManager;
import org.springframework.orm.jpa.LocalContainerEntityManagerFactoryBean;

import javax.persistence.EntityManagerFactory;
import javax.sql.DataSource;

@Configuration
@EnableJpaRepositories(
        entityManagerFactoryRef = "targetEntityManager",
        transactionManagerRef = "targetTransactionManager",
        basePackages = {"com.zadolnyi.reporthelper.repository"}
)
public class JPAConfig {

    @Bean(name = "targetEntityManager")
    public LocalContainerEntityManagerFactoryBean getTargetEntityManager(
            EntityManagerFactoryBuilder builder, @Qualifier("targetDataSource") DataSource targetDataSource){
        return builder
                .dataSource(targetDataSource)
                .packages("com.zadolnyi.reporthelper.model")
                .persistenceUnit("report")
                .build();
    }

    @Bean("targetDataSourceProperties")
    @Primary
    @ConfigurationProperties("target.datasource")
    public DataSourceProperties targetDataSourceProperties(){
        return new DataSourceProperties();
    }

    @Bean("targetDataSource")
    @Primary
    @ConfigurationProperties("target.datasource")
    public DataSource targetDataSource(@Qualifier("targetDataSourceProperties") DataSourceProperties targetDataSourceProperties) {
        return targetDataSourceProperties().initializeDataSourceBuilder().build();
    }

    @Bean(name = "targetTransactionManager")
    public JpaTransactionManager transactionManager(@Qualifier("targetEntityManager") EntityManagerFactory targetEntityManager){
        JpaTransactionManager transactionManager = new JpaTransactionManager();
        transactionManager.setEntityManagerFactory(targetEntityManager);
        return transactionManager;
    }
}
