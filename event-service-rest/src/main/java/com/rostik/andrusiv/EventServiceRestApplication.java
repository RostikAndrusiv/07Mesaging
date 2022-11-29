package com.rostik.andrusiv;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

//TODO: repository-> service. Done...
//TODO: check optional dependency. Done...
//TODO:profile -> ANNOTATION. Done...
//TODO:update idea. Done...
//TODO: const in separate class Common. Done
//TODO:move profiles in separate package. Done
//TODO:sonar. Done
//TODO:Exceptions. Done
//TODO:Generify EventServiceConsumer interface. Done
//TODO:updateEvent in service checks remove. Done
@SpringBootApplication
public class EventServiceRestApplication {

    public static void main(String[] args) {
        SpringApplication.run(EventServiceRestApplication.class, args);
    }
}
