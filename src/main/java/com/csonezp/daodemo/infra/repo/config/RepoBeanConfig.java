package com.csonezp.daodemo.infra.repo.config;

import com.csonezp.daodemo.domain.entity.User;
import com.csonezp.daodemo.infra.repo.register.RepoBeanDefinitionRegistry;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.jdbc.DataSourceBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.jdbc.core.JdbcTemplate;

import javax.sql.DataSource;

@Configuration
public class RepoBeanConfig {
    @Autowired
    JdbcTemplate jdbcTemplate;

    @Bean
    public DataSource datasource() {
        return DataSourceBuilder.create()
                .driverClassName("com.mysql.cj.jdbc.Driver")
                .url("jdbc:mysql://localhost:3306/test")
                .username("root")
                .password("*")
                .build();
    }

    @Bean
    public RepoBeanDefinitionRegistry repoBeanDefinitionRegistry(JdbcTemplate jdbcTemplate) {
        RepoBeanDefinitionRegistry registry = new RepoBeanDefinitionRegistry(jdbcTemplate);
        return registry;
    }
    @Bean
    public User myUser(JdbcTemplate jdbcTemplate){
        return new User();
    }

}
