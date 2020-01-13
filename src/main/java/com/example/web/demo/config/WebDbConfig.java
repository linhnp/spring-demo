package com.example.web.demo.config;

import com.zaxxer.hikari.HikariDataSource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.autoconfigure.jdbc.DataSourceProperties;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.boot.orm.jpa.EntityManagerFactoryBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;
import org.springframework.core.env.Environment;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.orm.jpa.JpaTransactionManager;
import org.springframework.orm.jpa.LocalContainerEntityManagerFactoryBean;
import org.springframework.transaction.PlatformTransactionManager;
import org.springframework.transaction.annotation.EnableTransactionManagement;

import javax.persistence.EntityManagerFactory;
import javax.sql.DataSource;
import java.util.HashMap;

@Configuration
@EnableTransactionManagement
@EnableJpaRepositories(
        entityManagerFactoryRef = "webEntityManagerFactory",
        basePackages = {"com.example.web.demo.multipledb.repository.web"},
        transactionManagerRef = "webtransactionManager"
        )
public class WebDbConfig {
    @Autowired
    private Environment env;

    @Primary
    @Bean(name = "webDataSourceProperties")
    @ConfigurationProperties("web.datasource")
    public DataSourceProperties dataSourceProperties(){
        return new DataSourceProperties();
    }

    @Primary
    @Bean(name = "webDataSource")
    @ConfigurationProperties("web.datasource.configuration")
    public DataSource dataSource(@Qualifier("webDataSourceProperties")DataSourceProperties webDataSourceProperties){
        return webDataSourceProperties.initializeDataSourceBuilder().type(HikariDataSource.class).build();
    }

    @Primary
    @Bean(name = "webEntityManagerFactory")
    public LocalContainerEntityManagerFactoryBean entityManagerFactoryBean(EntityManagerFactoryBuilder builder, @Qualifier("webDataSource")DataSource webDataSource){
        final HashMap<String, Object> properties = new HashMap<String, Object>();
        properties.put("hibernate.hbm2ddl.auto", env.getProperty("web.datasource.hibernate.hbm2ddl.auto"));
        properties.put("hibernate.dialect", env.getProperty("web.datasource.hibernate.dialect"));
//        properties.put("hibernate.naming_strategy", "org.hibernate.cfg.ImprovedNamingStrategy");

        return builder
                .dataSource(webDataSource)
                .packages("com.example.web.demo.multipledb.model.web")
                .persistenceUnit("web")
                .properties(properties)
                .build();
    }

    @Primary
    @Bean(name = "webtransactionManager")
    public PlatformTransactionManager transactionManager(@Qualifier("webEntityManagerFactory")EntityManagerFactory webEntityManagerFactory){
        return new JpaTransactionManager(webEntityManagerFactory);
    }
}
