package org.personal;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;
import org.springframework.core.env.Environment;
import org.springframework.jdbc.datasource.DriverManagerDataSource;
import org.springframework.orm.jpa.LocalContainerEntityManagerFactoryBean;
import org.springframework.orm.jpa.vendor.HibernateJpaVendorAdapter;

import javax.sql.DataSource;
import java.util.Properties;

@Configuration
@PropertySource("classpath:/application.properties")
public class AppConfig {
    @Autowired
    private Environment env;
    @Bean
    public DataSource dataSource(){
        var dataManager = new DriverManagerDataSource();
        dataManager.setDriverClassName(env.getRequiredProperty("spring.datasource.driverClassName"));
        dataManager.setUrl(env.getRequiredProperty("spring.datasource.url"));
        dataManager.setUsername(env.getRequiredProperty("spring.datasource.username"));
        dataManager.setPassword(env.getRequiredProperty("spring.datasource.password"));
        return dataManager;
    }
    private Properties hibProperties(){
        var prop = new Properties();
        prop.put("hibernate.dialect", env.getRequiredProperty("spring.jpa.properties.hibernate.dialect"));
        prop.put("hibernate.show_sql", env.getRequiredProperty("spring.jpa.properties.hibernate.format_sql"));
        return prop;
    }

    @Bean
    public LocalContainerEntityManagerFactoryBean entityManagerFactory(DataSource dataSource){
        final var emf = new LocalContainerEntityManagerFactoryBean();
        emf.setDataSource(dataSource);
        emf.setPackagesToScan("org.personal");

        final var vendorAdapter = new HibernateJpaVendorAdapter();
        emf.setJpaVendorAdapter(vendorAdapter);
        emf.setJpaProperties(hibProperties());
        return emf;
    }
}
