package mesaging.eventmessaging.rabbitmq;

import com.rostik.andrusiv.entity.EventDto;
import com.rostik.andrusiv.eventmessaging.rabbitmq.EventProducer;
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

import static com.rostik.andrusiv.common.Constants.*;
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
                .convertAndSend(ArgumentMatchers.eq(EXCHANGE), ArgumentMatchers.eq(CREATE_EVENT_NOTIFICATION_ROUTING_KEY), ArgumentMatchers.any(EventDto.class));
    }

    @Test
    void updateEventTest() {
        Assertions.assertThatCode(() -> subject.updateEvent(Mockito.mock(EventDto.class))).doesNotThrowAnyException();
        Mockito.verify(rabbitTemplateMock)
                .convertAndSend(ArgumentMatchers.eq(EXCHANGE), ArgumentMatchers.eq(UPDATE_EVENT_NOTIFICATION_ROUTING_KEY), ArgumentMatchers.any(EventDto.class));
    }

    @Test
    void deleteEventTest() {
        Assertions.assertThatCode(() -> subject.deleteEvent(1L)).doesNotThrowAnyException();
        Mockito.verify(rabbitTemplateMock)
                .convertAndSend(ArgumentMatchers.eq(EXCHANGE), ArgumentMatchers.eq(DELETE_EVENT_NOTIFICATION_ROUTING_KEY), anyLong());
    }
}