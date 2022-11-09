package com.rostik.andrusiv.mesaging.eventmessaging;

import ch.qos.logback.classic.Level;
import ch.qos.logback.classic.Logger;
import ch.qos.logback.classic.LoggerContext;
import com.rostik.andrusiv.mesaging.MemoryAppender;
import com.rostik.andrusiv.mesaging.eventmessaging.activemq.EventConsumer;
import com.rostik.andrusiv.mesaging.eventmessaging.activemq.EventProducer;
import com.rostik.andrusiv.mesaging.repository.EventRepository;
import com.rostik.andrusiv.mesaging.serviceapi.EventMessaging;
import com.rostik.andrusiv.mesaging.servicedto.entity.EventDto;
import com.rostik.andrusiv.mesaging.servicedto.entity.EventEntity;
import org.jetbrains.annotations.NotNull;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.util.ReflectionTestUtils;
import org.testcontainers.shaded.org.awaitility.Awaitility;

import java.util.Optional;
import java.util.concurrent.TimeUnit;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.when;

public abstract class ConsumerITtestBase {

    @Autowired
    protected EventMessaging eventProducer;

    @Autowired
    protected Consumer eventConsumer;

    @MockBean
    private EventRepository repository;

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
        ReflectionTestUtils.setField(eventProducer, "repository", repository);
        EventDto eventDto = createTestEventDto();
        when(repository.save(any())).thenReturn(Mockito.mock(EventEntity.class));
        eventProducer.createEvent(eventDto);
        Awaitility.await().atLeast(500, TimeUnit.MILLISECONDS);
        Thread.sleep(500);
    }

    protected void updateEventPerform() throws InterruptedException {
        when(repository.findById(any())).thenReturn(Optional.of(Mockito.mock(EventEntity.class)));
        when(repository.save(any())).thenReturn(Mockito.mock(EventEntity.class));
        EventDto eventDto = createTestEventDto();
        eventProducer.updateEvent(eventDto);
        Awaitility.await().atLeast(500, TimeUnit.MILLISECONDS);
        Thread.sleep(500);
    }

    protected void deleteEventPerform() throws InterruptedException {
        doNothing().when(repository).deleteById(anyLong());
        EventDto eventDto = createTestEventDto();
        eventProducer.deleteEvent(eventDto.getEventId());
        Awaitility.await().atLeast(500, TimeUnit.MILLISECONDS);
        Thread.sleep(500);
    }


    @NotNull
    protected EventDto createTestEventDto() {
        EventDto eventDto = new EventDto();
        eventDto.setEventId(1L);
        eventDto.setTitle("test");
        return eventDto;
    }

}
