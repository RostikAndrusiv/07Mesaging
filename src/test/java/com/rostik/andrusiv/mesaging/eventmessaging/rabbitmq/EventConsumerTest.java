package com.rostik.andrusiv.mesaging.eventmessaging.rabbitmq;

import com.rostik.andrusiv.mesaging.repository.EventRepository;
import com.rostik.andrusiv.mesaging.servicedto.entity.EventDto;
import org.junit.Ignore;
import org.junit.Test;
import org.junit.jupiter.api.BeforeEach;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.mockito.junit.MockitoJUnitRunner;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.amqp.rabbit.test.RabbitListenerTest;
import org.springframework.amqp.rabbit.test.RabbitListenerTestHarness;
import org.springframework.amqp.rabbit.test.context.SpringRabbitTest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.test.context.ActiveProfiles;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.verify;

@RunWith(MockitoJUnitRunner.class)
@SpringRabbitTest
@ActiveProfiles("rabbit")
public class EventConsumerTest {

    @InjectMocks
    private EventProducer subject = new EventProducer();

    private EventConsumer listener;

    @Mock
    private RabbitTemplate rabbitTemplateMock;

    @Mock
    private EventRepository repository;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.initMocks(this);
    }

    @Autowired
    private RabbitListenerTestHarness harness;

    @Test
    @Ignore
    public void testTwoWay() throws Exception {
        EventConsumer consumer = this.harness.getSpy("createEvent");
        assertNotNull(consumer);
        verify(consumer).createEvent(any(EventDto.class));
    }

//    @Test
//    public void testOneWay() throws Exception {
//        Listener listener = this.harness.getSpy("bar");
//        assertNotNull(listener);
//
//        LatchCountDownAndCallRealMethodAnswer answer = this.harness.getLatchAnswerFor("bar", 2);
//        doAnswer(answer).when(listener).foo(anyString(), anyString());
//
//        this.rabbitTemplate.convertAndSend(this.queue2.getName(), "bar");
//        this.rabbitTemplate.convertAndSend(this.queue2.getName(), "baz");
//
//        assertTrue(answer.await(10));
//        verify(listener).foo("bar", this.queue2.getName());
//        verify(listener).foo("baz", this.queue2.getName());
//    }

    @Configuration
    @RabbitListenerTest
    static class config{
    }
}
