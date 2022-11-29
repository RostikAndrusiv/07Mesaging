package mesaging.eventmessaging.kafka;

import ch.qos.logback.classic.Level;
import ch.qos.logback.classic.Logger;
import com.google.gson.Gson;
import com.rostik.andrusiv.entity.EventDto;
import com.rostik.andrusiv.eventmessaging.kafka.EventConsumer;
import com.rostik.andrusiv.eventmessaging.kafka.EventProducer;
import com.rostik.andrusiv.eventmessaging.kafka.KafkaConsumerConfig;
import com.rostik.andrusiv.eventmessaging.kafka.KafkaProducerConfig;
import mesaging.eventmessaging.ConsumerTestBase;
import org.apache.kafka.clients.consumer.ConsumerConfig;
import org.apache.kafka.clients.producer.ProducerConfig;
import org.apache.kafka.common.serialization.StringDeserializer;
import org.apache.kafka.common.serialization.StringSerializer;
import org.junit.ClassRule;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringBootConfiguration;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.test.context.TestConfiguration;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Import;
import org.springframework.kafka.annotation.EnableKafka;
import org.springframework.kafka.config.ConcurrentKafkaListenerContainerFactory;
import org.springframework.kafka.core.*;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.DynamicPropertyRegistry;
import org.springframework.test.context.DynamicPropertySource;
import org.springframework.test.context.junit4.SpringRunner;
import org.testcontainers.containers.KafkaContainer;
import org.testcontainers.utility.DockerImageName;

import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.TimeUnit;

@RunWith(SpringRunner.class)
@Import(EventConsumerTest.KafkaTestContainersConfiguration.class)
@ActiveProfiles("kafka")
@EnableAutoConfiguration
@SpringBootConfiguration
class EventConsumerTest extends ConsumerTestBase {

    private static final String LOGGER_NAME = "com.rostik.andrusiv.eventmessaging.kafka";

    protected static final Logger logger = (Logger) LoggerFactory.getLogger(LOGGER_NAME);

    private static final String MSG = "KafkaListener got msg from topic";

    @MockBean
    private KafkaConsumerConfig kafkaConsumerConfig;

    @MockBean
    private KafkaProducerConfig kafkaProducerConfig;

    @Autowired
    public KafkaTemplate<String, String> template;

    private final Gson gson = new Gson();

    @Autowired
    private EventConsumer consumer;

    @Autowired
    private EventProducer producer;

    @ClassRule
    public static KafkaContainer kafka = new KafkaContainer(DockerImageName.parse("confluentinc/cp-kafka:5.4.3"));

    static {
        kafka.start();
    }

    @BeforeEach
    public void beforeEach(){
        super.beforeTest(logger);
    }

    @DynamicPropertySource
    static void configure(DynamicPropertyRegistry registry){
        registry.add("spring.kafka.bootstrap-servers", kafka::getBootstrapServers);
    }

    @Test
    void createEvent() throws InterruptedException {
        EventDto eventDto = createTestEventDto();
        template.send("createEventTopic", gson.toJson(eventDto));
        TimeUnit.SECONDS.sleep(1);
        Assertions.assertEquals(1, memoryAppender.search(MSG, Level.INFO).size());
    }

    @Test
    void updateEvent() throws InterruptedException {
        EventDto eventDto = createTestEventDto();
        template.send("updateEventTopic", gson.toJson(eventDto));
        TimeUnit.SECONDS.sleep(1);
        Assertions.assertEquals(1, memoryAppender.search(MSG, Level.INFO).size());
    }

    @Test
    void deleteEvent() throws InterruptedException {
        EventDto eventDto = createTestEventDto();
        template.send("deleteEventTopic", "1");
        TimeUnit.SECONDS.sleep(1);
        Assertions.assertEquals(1, memoryAppender.search(MSG, Level.INFO).size());
    }

    @TestConfiguration
    @EnableKafka
    @ComponentScan(basePackages = "com.rostik.andrusiv")
    static class KafkaTestContainersConfiguration {

        @Bean
        ConcurrentKafkaListenerContainerFactory<Integer, String> kafkaListenerContainerFactory() {
            ConcurrentKafkaListenerContainerFactory<Integer, String> factory = new ConcurrentKafkaListenerContainerFactory<>();
            factory.setConsumerFactory(consumerFactory());
            return factory;
        }

        @Bean
        public ConsumerFactory<Integer, String> consumerFactory() {
            return new DefaultKafkaConsumerFactory<>(consumerConfigs());
        }

        @Bean
        public Map<String, Object> consumerConfigs() {
            Map<String, Object> props = new HashMap<>();
            //FIXME: doesnt work
            props.put(ConsumerConfig.BOOTSTRAP_SERVERS_CONFIG, kafka.getBootstrapServers());
            props.put(ConsumerConfig.AUTO_OFFSET_RESET_CONFIG, "earliest");
            props.put(ConsumerConfig.GROUP_ID_CONFIG, "groupId");
            props.put(ConsumerConfig.KEY_DESERIALIZER_CLASS_CONFIG, StringDeserializer.class);
            props.put(ConsumerConfig.VALUE_DESERIALIZER_CLASS_CONFIG, StringDeserializer.class);
            return props;
        }

        @Bean
        public ProducerFactory<String, String> producerFactory() {
            Map<String, Object> configProps = new HashMap<>();
            configProps.put(ProducerConfig.BOOTSTRAP_SERVERS_CONFIG, kafka.getBootstrapServers());
            configProps.put(ProducerConfig.KEY_SERIALIZER_CLASS_CONFIG, StringSerializer.class);
            configProps.put(ProducerConfig.VALUE_SERIALIZER_CLASS_CONFIG, StringSerializer.class);
            return new DefaultKafkaProducerFactory<>(configProps);
        }

        @Bean
        public KafkaTemplate<String, String> kafkaTemplate() {
            return new KafkaTemplate<>(producerFactory());
        }

    }
}