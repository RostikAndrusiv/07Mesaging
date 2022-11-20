package mesaging.eventmessaging.rabbitmq;

import com.rostik.andrusiv.entity.EventDto;
import com.rostik.andrusiv.entity.EventEntity;
import com.rostik.andrusiv.eventmessaging.rabbitmq.EventProducer;
import com.rostik.andrusiv.eventmessaging.rabbitmq.RabbitMQConfig;
import com.rostik.andrusiv.repository.EventRepository;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.mockito.*;
import org.mockito.junit.MockitoJUnitRunner;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ActiveProfiles;

import java.util.Optional;

import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.ArgumentMatchers.eq;

@ActiveProfiles("rabbit")
@RunWith(MockitoJUnitRunner.class)
class EventProducerTest {

    @InjectMocks
    @Autowired
    private EventProducer subject;

    @Mock
    private RabbitTemplate rabbitTemplateMock;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.initMocks(this);
    }

    @AfterEach
    void tearDown() {
    }

    @Test
    void createEventTest() {
        Assertions.assertThatCode(() -> subject.createEvent(Mockito.mock(EventDto.class))).doesNotThrowAnyException();
        Mockito.verify(rabbitTemplateMock)
                .convertAndSend(ArgumentMatchers.eq(RabbitMQConfig.EXCHANGE), ArgumentMatchers.eq(RabbitMQConfig.CREATE_EVENT_NOTIFICATION_ROUTING_KEY), ArgumentMatchers.any(EventDto.class));
    }

    @Test
    void updateEventTest() {
        Assertions.assertThatCode(() -> subject.updateEvent(Mockito.mock(EventDto.class))).doesNotThrowAnyException();
        Mockito.verify(rabbitTemplateMock)
                .convertAndSend(ArgumentMatchers.eq(RabbitMQConfig.EXCHANGE), ArgumentMatchers.eq(RabbitMQConfig.UPDATE_EVENT_NOTIFICATION_ROUTING_KEY), ArgumentMatchers.any(EventDto.class));
    }

    @Test
    void deleteEventTest() {
        Assertions.assertThatCode(() -> subject.deleteEvent(1L)).doesNotThrowAnyException();
        Mockito.verify(rabbitTemplateMock)
                .convertAndSend(ArgumentMatchers.eq(RabbitMQConfig.EXCHANGE), ArgumentMatchers.eq(RabbitMQConfig.DELETE_EVENT_NOTIFICATION_ROUTING_KEY), anyLong());
    }
}