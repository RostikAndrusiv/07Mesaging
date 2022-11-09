package com.rostik.andrusiv.mesaging.eventmessaging.rabbitmq;

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
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.ActiveProfiles;

import javax.jms.Queue;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThatCode;
import static org.mockito.ArgumentMatchers.*;
import static org.mockito.Mockito.*;

@ActiveProfiles("rabbit")
@RunWith(MockitoJUnitRunner.class)
class EventProducerTest {

    @InjectMocks
    @Autowired
    private EventProducer subject;

    @Mock
    private RabbitTemplate rabbitTemplateMock;

    @Mock
    private EventRepository repository;

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
        assertThatCode(() -> subject.createEvent(Mockito.mock(EventDto.class))).doesNotThrowAnyException();
        Mockito.verify(rabbitTemplateMock)
                .convertAndSend(eq(RabbitMQConfig.EXCHANGE), eq(RabbitMQConfig.CREATE_EVENT_NOTIFICATION_ROUTING_KEY), any(EventDto.class));
        Mockito.verify(repository, times(1)).save(any(EventEntity.class));
    }

    @Test
    void updateEventTest() {
        when(repository.findById(any())).thenReturn(Optional.of(Mockito.mock(EventEntity.class)));
        when(repository.save(any())).thenReturn(Mockito.mock(EventEntity.class));
        assertThatCode(() -> subject.updateEvent(Mockito.mock(EventDto.class))).doesNotThrowAnyException();
        Mockito.verify(rabbitTemplateMock)
                .convertAndSend(eq(RabbitMQConfig.EXCHANGE), eq(RabbitMQConfig.UPDATE_EVENT_NOTIFICATION_ROUTING_KEY), any(EventDto.class));
        Mockito.verify(repository, times(1)).save(any(EventEntity.class));
    }

    @Test
    void deleteEventTest() {
        doNothing().when(repository).deleteById(anyLong());
        assertThatCode(() -> subject.deleteEvent(anyLong())).doesNotThrowAnyException();
        Mockito.verify(rabbitTemplateMock)
                .convertAndSend(eq(RabbitMQConfig.EXCHANGE), eq(RabbitMQConfig.DELETE_EVENT_NOTIFICATION_ROUTING_KEY), anyLong());
        Mockito.verify(repository, times(1)).deleteById(anyLong());
    }
}