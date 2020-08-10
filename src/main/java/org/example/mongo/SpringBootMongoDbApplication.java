package org.example.mongo;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;

@SpringBootApplication
@EnableAutoConfiguration
@ComponentScan(basePackages = {"org.example.mongo.repos","org.example.mongo.config"})
public class SpringBootMongoDbApplication {

    public static void main(String... args) {
        SpringApplication.run(SpringBootMongoDbApplication.class, args);
    }
}
