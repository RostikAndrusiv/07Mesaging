package com.rostik.andrusiv.mesaging.eventmessaging.activemq;

import com.rostik.andrusiv.mesaging.repository.EventRepository;
import com.rostik.andrusiv.mesaging.servicedto.entity.EventDto;
import com.rostik.andrusiv.mesaging.servicedto.entity.EventEntity;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
import org.mockito.junit.MockitoJUnitRunner;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jms.core.JmsTemplate;
import org.springframework.test.context.ActiveProfiles;


import javax.jms.Queue;

import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThatCode;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.*;
import static org.mockito.Mockito.times;

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
        when(repository.save(any())).thenReturn(Mockito.mock(EventEntity.class));
        assertThatCode(() -> eventProducer.createEvent(Mockito.mock(EventDto.class))).doesNotThrowAnyException();
        Mockito.verify(jmsTemplateMock)
                .convertAndSend(eq(createEventQueue), any(EventDto.class));
        Mockito.verify(repository, times(1)).save(any(EventEntity.class));
    }

    @Test
    void updateEventTest() {
        when(repository.findById(any())).thenReturn(Optional.of(Mockito.mock(EventEntity.class)));
        when(repository.save(any())).thenReturn(Mockito.mock(EventEntity.class));
        assertThatCode(() -> eventProducer.updateEvent(Mockito.mock(EventDto.class))).doesNotThrowAnyException();
        Mockito.verify(jmsTemplateMock)
                .convertAndSend(eq(updateEventQueue), any(EventDto.class));
        Mockito.verify(repository, times(1)).save(any(EventEntity.class));
    }

    @Test
    void deleteEventTest() {
        doNothing().when(repository).deleteById(anyLong());
        assertThatCode(() -> eventProducer.deleteEvent(anyLong())).doesNotThrowAnyException();
        Mockito.verify(jmsTemplateMock)
                .convertAndSend(eq(deleteEventQueue), anyLong());
        Mockito.verify(repository, times(1)).deleteById(anyLong());
    }
}