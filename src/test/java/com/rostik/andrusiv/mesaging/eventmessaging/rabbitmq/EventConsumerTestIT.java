package com.rostik.andrusiv.mesaging.eventmessaging.rabbitmq;

import ch.qos.logback.classic.Level;
import ch.qos.logback.classic.Logger;
import com.rostik.andrusiv.mesaging.eventmessaging.ConsumerITtestBase;
import com.rostik.andrusiv.mesaging.repository.EventRepository;
import com.rostik.andrusiv.mesaging.servicedto.entity.EventDto;
import com.rostik.andrusiv.mesaging.servicedto.entity.EventEntity;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.context.*;
import org.testcontainers.containers.RabbitMQContainer;
import org.testcontainers.junit.jupiter.Container;
import org.testcontainers.junit.jupiter.Testcontainers;
import org.testcontainers.utility.DockerImageName;

import java.util.Optional;
import java.util.concurrent.TimeUnit;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.when;

@SpringBootTest
@DirtiesContext
@ActiveProfiles("rabbit")
@Testcontainers
@TestPropertySource(locations = {"classpath:application.properties"})
class EventConsumerTestIT extends ConsumerITtestBase {

    private static final String LOGGER_NAME = "com.rostik.andrusiv.mesaging.eventmessaging.rabbitmq";

    protected static final Logger logger = (Logger) LoggerFactory.getLogger(LOGGER_NAME);

    private static final String MSG = "RabbitListener got msg from queue";

    @Container
    static RabbitMQContainer container = new RabbitMQContainer(
            new DockerImageName("rabbitmq", "3-management")
    );

    @BeforeEach
    public void beforeEach(){
        super.beforeTest(logger);
    }

    @DynamicPropertySource
    static void configure(DynamicPropertyRegistry registry){
        registry.add("spring.rabbitmq.port", container::getAmqpPort);
        registry.add("spring.rabbitmq.host", container::getHost);
    }

    @Test
    void createEvent() throws InterruptedException {
        createEventPerform();
        Assertions.assertEquals(1, memoryAppender.search(MSG, Level.INFO).size());
    }

    @Test
    void updateEvent() throws InterruptedException {
        updateEventPerform();
        Assertions.assertEquals(1, memoryAppender.search(MSG, Level.INFO).size());
    }

    @Test
    void deleteEvent() throws InterruptedException {
        deleteEventPerform();
        Assertions.assertEquals(1, memoryAppender.search(MSG, Level.INFO).size());
    }
}