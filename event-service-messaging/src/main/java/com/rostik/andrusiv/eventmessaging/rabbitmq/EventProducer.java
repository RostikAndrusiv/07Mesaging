package com.rostik.andrusiv.eventmessaging.rabbitmq;

import com.rostik.andrusiv.entity.EventDto;
import com.rostik.andrusiv.profile.RabbitMQProfile;
import com.rostik.andrusiv.eventserviceapi.EventMessaging;
import org.springframework.amqp.core.TopicExchange;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import static com.rostik.andrusiv.common.Constants.*;

@Component
@RabbitMQProfile
public class EventProducer implements EventMessaging {

    @Autowired
    private RabbitTemplate template;

    @Autowired
    private TopicExchange topicExchange;

    @Override
    public void createEvent(EventDto eventDto) {
        template.convertAndSend(EXCHANGE, CREATE_EVENT_NOTIFICATION_ROUTING_KEY, eventDto);
    }

    @Override
    public void updateEvent(EventDto eventDto) {
        template.convertAndSend(EXCHANGE, UPDATE_EVENT_NOTIFICATION_ROUTING_KEY, eventDto);
    }

    @Override
    public void deleteEvent(Long id) {
        template.convertAndSend(EXCHANGE, DELETE_EVENT_NOTIFICATION_ROUTING_KEY, id);
    }
}
