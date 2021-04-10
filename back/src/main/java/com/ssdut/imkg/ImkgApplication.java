package com.ssdut.imkg;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.neo4j.repository.config.EnableNeo4jRepositories;

@MapperScan("com.ssdut.imkg.mapper")
@SpringBootApplication
public class ImkgApplication {

    public static void main(String[] args) {
        SpringApplication.run(ImkgApplication.class, args);
    }

}
