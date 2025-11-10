package com.example.demo.beans;

import java.util.logging.Logger;
import com.example.demo.mybatis.PostgreSQLMapper;
import org.springframework.transaction.annotation.Transactional;

public class PostgreSQLShowCase {
    static final Logger logger = Logger.getLogger("DataDno");
    private PostgreSQLMapper postgreSQLMapper;

    public PostgreSQLMapper getPostgreSQLMapper() {
        return postgreSQLMapper;
    }

    public void setPostgreSQLMapper(PostgreSQLMapper postgreSQLMapper) {
        this.postgreSQLMapper = postgreSQLMapper;
    }

    @Transactional("idManagerPostgres")
    public String getBodyByCode(String code) {
        return postgreSQLMapper.getBodyByCode(code);
    }

}
