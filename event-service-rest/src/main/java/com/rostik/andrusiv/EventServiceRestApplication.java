package com.rostik.andrusiv;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

//TODO: repository-> service. Done...
//TODO: check optional dependency. Done...
//TODO:profile -> ANNOTATION. Done...
//TODO:update idea. Done...
@SpringBootApplication
@EnableJpaRepositories
public class EventServiceRestApplication {

    public static void main(String[] args) {
        SpringApplication.run(EventServiceRestApplication.class, args);
    }
}
