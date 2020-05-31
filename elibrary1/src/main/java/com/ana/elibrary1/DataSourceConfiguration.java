/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.ana.elibrary1;

import java.util.Properties;
import javax.sql.DataSource;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.jdbc.datasource.DriverManagerDataSource;
import org.springframework.orm.hibernate5.HibernateTransactionManager;
import org.springframework.orm.hibernate5.LocalSessionFactoryBean;
import org.springframework.transaction.annotation.EnableTransactionManagement;

/**
 *
 * @author ana.radun
 */

@Configuration
@EnableTransactionManagement
public class DataSourceConfiguration {
    @Value("${db.driver}")
    private String DRIVER;
    
    @Value("${db.url}")
    private String URL;
    
    @Value("${db.username}")
    private String USERNAME;
    
    @Value("${db.password}")
    private String PASSWORD;
    
    @Value("${hibernate.dialect}")
    private String DIALECT;
    
    @Value("${hibernate.show_sql}")
    private String SHOW_SQL;
    
    @Value("${hibernate.hbm2ddl.auto}")
    private String HBM2DDL_AUTO;
    
    @Value("${entitymanager.packagesToScan}")
    private String PACKAGES_TO_SCAN;
    
    @Value("${hibernate.use_sql_comments}")
    private String USE_SQL_COMMENTS;
    
    @Value("${hibernate.format_sql}")
    private String FORMAT_SQL;
    
    @Value("${hibernate.type}")
    private String HIBERNATE_TYPE;
    
    @Bean
    public DataSource dataSource() {
        DriverManagerDataSource dataSource = new DriverManagerDataSource();
        dataSource.setDriverClassName(DRIVER);
        dataSource.setUrl(URL);
        dataSource.setUsername(USERNAME);
        dataSource.setPassword(PASSWORD);
        return dataSource;
    }
    
    @Bean
    public LocalSessionFactoryBean sessionFactory() {
        LocalSessionFactoryBean sessionFactory = new LocalSessionFactoryBean();
        sessionFactory.setDataSource(dataSource());
        sessionFactory.setPackagesToScan(PACKAGES_TO_SCAN);
        Properties hibernateProperties = new Properties();
        hibernateProperties.put("hibernate.dialect", DIALECT);
        hibernateProperties.put("hibernate.show_sql", SHOW_SQL);
        hibernateProperties.put("hibernate.use_sql_comments", USE_SQL_COMMENTS);
        hibernateProperties.put("hibernate.format_sql", FORMAT_SQL);
        hibernateProperties.put("hibernate.hbm2ddl.auto", HBM2DDL_AUTO);
        hibernateProperties.put("hibernate.type", HIBERNATE_TYPE);
        sessionFactory.setHibernateProperties(hibernateProperties);
        return sessionFactory;
    }
    
    @Bean
    public HibernateTransactionManager transactionManager() {
        HibernateTransactionManager transactionManager = new HibernateTransactionManager();
        transactionManager.setSessionFactory(sessionFactory().getObject());
        return transactionManager;
    }
    
}
