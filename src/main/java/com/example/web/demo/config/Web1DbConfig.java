package com.example.web.demo.config;

import com.zaxxer.hikari.HikariDataSource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.autoconfigure.jdbc.DataSourceProperties;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.boot.orm.jpa.EntityManagerFactoryBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
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
        entityManagerFactoryRef = "web1EntityManagerFactory",
        basePackages = {"com.example.web.demo.multipledb.repository.web1"},
        transactionManagerRef = "web1TransactionManager"
        )
public class Web1DbConfig {
    @Autowired
    private Environment env;

    @Bean(name = "web1DataSourceProperties")
    @ConfigurationProperties("web1.datasource")
    public DataSourceProperties dataSourceProperties(){
        return new DataSourceProperties();
    }

    @Bean(name = "web1DataSource")
    @ConfigurationProperties("web1.datasource.configuration")
    public DataSource dataSource(@Qualifier("web1DataSourceProperties")DataSourceProperties web1DataSourceProperties){
        return web1DataSourceProperties.initializeDataSourceBuilder().type(HikariDataSource.class).build();
    }

    @Bean(name = "web1EntityManagerFactory")
    public LocalContainerEntityManagerFactoryBean entityManagerFactoryBean(EntityManagerFactoryBuilder builder, @Qualifier("web1DataSource")DataSource web1DataSource){
        final HashMap<String, Object> properties = new HashMap<String, Object>();
        properties.put("hibernate.hbm2ddl.auto", env.getProperty("web1.datasource.hibernate.hbm2ddl.auto"));
        properties.put("hibernate.dialect", env.getProperty("web1.datasource.hibernate.dialect"));

        return builder
                .dataSource(web1DataSource)
                .packages("com.example.web.demo.multipledb.model.web1")
                .persistenceUnit("web1")
                .properties(properties)
                .build();
    }

    @Bean(name = "web1TransactionManager")
    public PlatformTransactionManager transactionManager(@Qualifier("web1EntityManagerFactory")EntityManagerFactory web1EntityManagerFactory){
        return new JpaTransactionManager(web1EntityManagerFactory);
    }
}
