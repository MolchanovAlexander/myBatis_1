package com.example.demo.mybatis;

public interface PostgreSQLMapper {
    //SELECT body FROM testdb.financedata WHERE param = ?
    String getBodyByCode(String code);
}
