package com.ts.postmaster.controller;

import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.test.context.DynamicPropertyRegistry;
import org.springframework.test.context.DynamicPropertySource;
import org.testcontainers.containers.PostgreSQLContainer;

import java.io.File;

/**
 * @author toyewole
 */

public abstract class AbstractIntegrationTest {

    private static final PostgreSQLContainer POSTGRE_SQL_CONTAINER = new PostgreSQLContainer(PostgreSQLContainer.IMAGE);

    static {
//        ORACLE_CONTAINER.start();
        POSTGRE_SQL_CONTAINER.start();
    }

    @DynamicPropertySource
    static void registerProperties(DynamicPropertyRegistry registry) {
        //database
        registry.add("spring.datasource.url", POSTGRE_SQL_CONTAINER::getJdbcUrl);
        registry.add("spring.datasource.username", POSTGRE_SQL_CONTAINER::getUsername);
        registry.add("spring.datasource.password", POSTGRE_SQL_CONTAINER::getPassword);
  //      registry.add("spring.jpa.properties.hibernate.dialect", ()->"org.hibernate.dialect.PostgreSQL95Dialect");



    }
}
