package com.example.demo.beans;

import java.util.logging.Logger;

public class PostgreSQLShowCase {
    static final Logger logger = Logger.getLogger("DataDno");
    private PostgreSQLMapper postgreSQLMapper;

    public PostgreSQLMapper getPostgreSQLMapper() {
        return postgreSQLMapper;
    }

    public void setPostgreSQLMapper(PostgreSQLMapper postgreSQLMapper) {
        this.postgreSQLMapper = postgreSQLMapper;
    }



}
