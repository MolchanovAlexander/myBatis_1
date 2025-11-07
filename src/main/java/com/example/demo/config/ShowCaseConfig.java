package com.example.demo.config;

import com.example.demo.beans.PostgreSQLShowCase;
import org.mybatis.spring.mapper.MapperFactoryBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class ShowCaseConfig {

    @Bean
    public PostgreSQLShowCase PostgreSQLShowCase(PostgreSQLMapper sqlMapper){
        PostgreSQLShowCase postgreSQLShowCase = new PostgreSQLShowCase();
        postgreSQLShowCase.setPostgreSQLMapper(null);
        return postgreSQLShowCase;
    }

    @Bean
    public MapperFactoryBean<PostgreSQLMapper> sqlMapper
}
