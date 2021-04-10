package com.ssdut.imkg.graph;

import org.neo4j.driver.AuthTokens;
import org.neo4j.driver.Driver;
import org.neo4j.driver.GraphDatabase;
import org.neo4j.driver.Session;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.neo4j.repository.config.EnableNeo4jRepositories;
import org.springframework.transaction.annotation.EnableTransactionManagement;


/**
 * @Author Fan Yuanxin
 * @Date 2021/4/5 9:54
 * @Version 1.0
 */
//@Configuration
//@EnableNeo4jRepositories(basePackages = "com.ssdut.imkg.graph.repositories")
//@EnableTransactionManagement
public class Neo4jConfig {
    @Value("${spring.neo4j.url}")
    private String url;

    @Value("${spring.neo4j.username}")
    private String username;

    @Value("${spring.neo4j.password}")
    private String password;

    @Bean(name = "session")
    public Session neo4jSession(){
        Driver driver = GraphDatabase.driver(url, AuthTokens.basic(username,password));
        return driver.session();
    }
}
