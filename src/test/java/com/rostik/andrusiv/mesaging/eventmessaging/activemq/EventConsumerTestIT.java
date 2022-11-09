package com.rostik.andrusiv.mesaging.eventmessaging.activemq;

import ch.qos.logback.classic.Level;
import ch.qos.logback.classic.Logger;
import com.rostik.andrusiv.mesaging.eventmessaging.ConsumerITtestBase;
import com.rostik.andrusiv.mesaging.repository.EventRepository;
import com.rostik.andrusiv.mesaging.servicedto.entity.EventDto;
import com.rostik.andrusiv.mesaging.servicedto.entity.EventEntity;
import org.apache.activemq.ActiveMQConnectionFactory;
import org.junit.ClassRule;
import org.junit.Ignore;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.context.TestConfiguration;
import org.springframework.context.annotation.Bean;
import org.springframework.jms.annotation.EnableJms;
import org.springframework.jms.core.JmsTemplate;
import org.springframework.jms.support.converter.MappingJackson2MessageConverter;
import org.springframework.jms.support.converter.MessageConverter;
import org.springframework.jms.support.converter.MessageType;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.context.*;
import org.springframework.test.util.ReflectionTestUtils;
import org.testcontainers.containers.GenericContainer;
import org.testcontainers.junit.jupiter.Testcontainers;
import org.testcontainers.shaded.org.awaitility.Awaitility;
import org.testcontainers.utility.DockerImageName;

import javax.jms.ConnectionFactory;
import java.util.Optional;
import java.util.concurrent.TimeUnit;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.when;

@SpringBootTest
@ActiveProfiles("activemq")
@Testcontainers
@TestPropertySource(locations = {"classpath:application.properties"})
@DirtiesContext
public class EventConsumerTestIT extends ConsumerITtestBase {

    private static final String LOGGER_NAME = "com.rostik.andrusiv.mesaging.eventmessaging.activemq";

    protected static final Logger logger = (Logger) LoggerFactory.getLogger(LOGGER_NAME);

    private static final String MSG = "JMSListener got msg from queue";

    @ClassRule
    public static GenericContainer<?> activeMqContainer =
            new GenericContainer<>(DockerImageName.parse("rmohr/activemq:5.14.3")).withExposedPorts(61616);

    @BeforeEach
    public void beforeEach(){
        super.beforeTest(logger);
    }

    @BeforeEach
    void setUp() {
        MockitoAnnotations.initMocks(this);
    }

    static {
        activeMqContainer.start();
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

    @TestConfiguration
    @EnableJms
    public static class TestConfig {

        @Bean
        public ConnectionFactory activeMQConnectionFactory() {
            String brokerUrlFormat = "tcp://%s:%d";
            String brokerUrl = String.format(brokerUrlFormat, activeMqContainer.getHost(), activeMqContainer.getFirstMappedPort());
            return new ActiveMQConnectionFactory(brokerUrl);
        }

        @Bean
        public MessageConverter jacksonJmsMessageConverter() {
            MappingJackson2MessageConverter converter = new MappingJackson2MessageConverter();
            converter.setTargetType(MessageType.TEXT);
            converter.setTypeIdPropertyName("_type");
            return converter;
        }

        @Bean
        public JmsTemplate jmsTemplate() {
            JmsTemplate jmsTemplate = new JmsTemplate(activeMQConnectionFactory());
            jmsTemplate.setMessageConverter(jacksonJmsMessageConverter());
            return jmsTemplate;
        }
    }
}
