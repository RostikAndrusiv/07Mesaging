package mesaging.eventmessaging.activemq;

import com.rostik.andrusiv.entity.EventDto;
import com.rostik.andrusiv.eventmessaging.activemq.EventProducer;
import com.rostik.andrusiv.repository.EventRepository;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.mockito.*;
import org.mockito.junit.MockitoJUnitRunner;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jms.core.JmsTemplate;
import org.springframework.test.context.ActiveProfiles;
import javax.jms.Queue;

import static org.mockito.ArgumentMatchers.eq;

@ActiveProfiles("activemq")
@RunWith(MockitoJUnitRunner.class)
class EventProducerTest {

    @InjectMocks
    @Autowired
    private EventProducer eventProducer;

    @Mock
    private JmsTemplate jmsTemplateMock;

    @Mock
    private EventRepository repository;

    @Mock
    private Queue createEventQueue;

    @Mock
    private Queue updateEventQueue;

    @Mock
    private Queue deleteEventQueue;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.initMocks(this);
    }

    @AfterEach
    void tearDown() {
    }

    @Test
    void createEventTest() {
        Assertions.assertThatCode(() -> eventProducer.createEvent(Mockito.mock(EventDto.class))).doesNotThrowAnyException();
        Mockito.verify(jmsTemplateMock)
                .convertAndSend(eq(createEventQueue), ArgumentMatchers.any(EventDto.class));
    }

    @Test
    void updateEventTest() {
        Assertions.assertThatCode(() -> eventProducer.updateEvent(Mockito.mock(EventDto.class))).doesNotThrowAnyException();
        Mockito.verify(jmsTemplateMock)
                .convertAndSend(eq(updateEventQueue), ArgumentMatchers.any(EventDto.class));
    }

    @Test
    void deleteEventTest() {
        Assertions.assertThatCode(() -> eventProducer.deleteEvent(1L)).doesNotThrowAnyException();
        Mockito.verify(jmsTemplateMock)
                .convertAndSend(eq(deleteEventQueue), ArgumentMatchers.anyLong());
    }
}