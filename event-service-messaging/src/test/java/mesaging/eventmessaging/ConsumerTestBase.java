package mesaging.eventmessaging;

import ch.qos.logback.classic.Level;
import ch.qos.logback.classic.Logger;
import ch.qos.logback.classic.LoggerContext;
import com.rostik.andrusiv.entity.EventDto;
import com.rostik.andrusiv.eventserviceapi.EventMessaging;
import com.rostik.andrusiv.eventserviceapi.EventServiceConsumer;
import mesaging.MemoryAppender;
import com.rostik.andrusiv.repository.EventRepository;
import org.jetbrains.annotations.NotNull;
import org.junit.jupiter.api.AfterEach;
import org.mockito.MockitoAnnotations;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringBootConfiguration;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.TestPropertySource;
import org.testcontainers.junit.jupiter.Testcontainers;

import java.util.concurrent.TimeUnit;

@SpringBootTest
@Testcontainers
@TestPropertySource(locations = {"classpath:application.properties"})
public abstract class ConsumerTestBase {

    @Autowired
    protected EventMessaging eventProducer;

    @Autowired
    protected EventServiceConsumer eventConsumer;

    @MockBean
    protected EventRepository repository;

    protected static MemoryAppender memoryAppender;

    public void beforeTest(Logger logger) {
        MockitoAnnotations.initMocks(this);
        memoryAppender = new MemoryAppender();
        memoryAppender.setContext((LoggerContext) LoggerFactory.getILoggerFactory());
        logger.setLevel(Level.DEBUG);
        logger.addAppender(memoryAppender);
        memoryAppender.start();
    }

    @AfterEach
    public void afterTest() {
        memoryAppender.reset();
        memoryAppender.stop();
    }

    protected void createEventPerform() throws InterruptedException {
        EventDto eventDto = createTestEventDto();
        eventProducer.createEvent(eventDto);
        TimeUnit.SECONDS.sleep(1);
    }

    protected void updateEventPerform() throws InterruptedException {

        EventDto eventDto = createTestEventDto();
        eventProducer.updateEvent(eventDto);
        TimeUnit.SECONDS.sleep(1);
    }

    protected void deleteEventPerform() throws InterruptedException {
        EventDto eventDto = createTestEventDto();
        eventProducer.deleteEvent(eventDto.getEventId());
        TimeUnit.SECONDS.sleep(1);
    }


    @NotNull
    protected EventDto createTestEventDto() {
        EventDto eventDto = new EventDto();
        eventDto.setEventId(1L);
        eventDto.setTitle("test");
        return eventDto;
    }

}
