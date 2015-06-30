package com.examplecorp.phonecat.configuration;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.repository.config.EnableMongoRepositories;

import com.mongodb.Mongo;

@Configuration
@EnableMongoRepositories(basePackages="com.examplecorp.phonecat.repository")
public class MongoConfiguration {

    @Bean
    public Mongo mongo() throws Exception {
        return new Mongo("localhost");
    }

    @Bean
    public MongoTemplate mongoTemplate() throws Exception {
        return new MongoTemplate(mongo(), "mydatabase");
    }
}
