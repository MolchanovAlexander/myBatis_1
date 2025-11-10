package com.example.demo.config;

import javax.sql.DataSource;
import java.io.IOException;
import com.example.demo.beans.PostgreSQLShowCase;
import com.example.demo.mybatis.PostgreSQLMapper;
import org.apache.ibatis.session.SqlSessionFactory;
import org.mybatis.spring.SqlSessionFactoryBean;
import org.mybatis.spring.mapper.MapperFactoryBean;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.io.ClassPathResource;
import org.springframework.core.io.support.PathMatchingResourcePatternResolver;
import org.springframework.jdbc.datasource.DataSourceTransactionManager;

@Configuration
public class ShowCaseConfig {

    @Bean
    public PostgreSQLShowCase PostgreSQLShowCase(PostgreSQLMapper sqlMapper){
        PostgreSQLShowCase postgreSQLShowCase = new PostgreSQLShowCase();
        postgreSQLShowCase.setPostgreSQLMapper(sqlMapper);
        return postgreSQLShowCase;
    }

    @Bean
    public MapperFactoryBean<PostgreSQLMapper> sqlMapper(@Qualifier("postgreSqlSessionFactory")SqlSessionFactory sqlSessionFactory) {
        MapperFactoryBean<PostgreSQLMapper> factoryBean = new MapperFactoryBean<>(PostgreSQLMapper.class);
        factoryBean.setSqlSessionFactory(sqlSessionFactory);
        return factoryBean;
    }

    @Bean
    public SqlSessionFactoryBean postgreSqlSessionFactory(@Qualifier("psgrs_ds_14102025") DataSource dataSource) throws IOException {
        SqlSessionFactoryBean factoryBean = new SqlSessionFactoryBean();
        factoryBean.setDataSource(dataSource);
        factoryBean.setConfigLocation(new ClassPathResource("mybatis-config.xml"));
        factoryBean.setMapperLocations(
                new PathMatchingResourcePatternResolver().getResources("classpath*:mybatis/*.xml")
        );
        return factoryBean;
    }

    @Bean
    public DataSourceTransactionManager idManagerPostgres(@Qualifier("psgrs_ds_14102025") DataSource dataSource) {
        return new DataSourceTransactionManager(dataSource);
    }
}
