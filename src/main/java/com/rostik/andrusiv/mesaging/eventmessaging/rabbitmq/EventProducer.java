package com.rostik.andrusiv.mesaging.eventmessaging.rabbitmq;

import com.rostik.andrusiv.mesaging.repository.EventRepository;
import com.rostik.andrusiv.mesaging.serviceapi.EventMessaging;
import com.rostik.andrusiv.mesaging.servicedto.entity.EventDto;
import com.rostik.andrusiv.mesaging.servicedto.entity.EventEntity;
import com.rostik.andrusiv.mesaging.util.EventMapper;
import org.springframework.amqp.core.TopicExchange;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Component;

import static com.rostik.andrusiv.mesaging.eventmessaging.rabbitmq.RabbitMQConfig.*;

@Component
@Profile("rabbit")
public class EventProducer implements EventMessaging {

    @Autowired
    private RabbitTemplate template;

    @Autowired
    private EventRepository repository;

    @Autowired
    private TopicExchange topicExchange;

    @Override
    public void createEvent(EventDto eventDto) {
        repository.save(EventMapper.INSTANCE.dtoToEventModel(eventDto));
        template.convertAndSend(EXCHANGE, CREATE_EVENT_NOTIFICATION_ROUTING_KEY, eventDto);
    }

    @Override
    public void updateEvent(EventDto eventDto) {
        if (eventDto != null && eventDto.getEventId() != null) {
            EventEntity persistedEvent = repository.findById(eventDto.getEventId()).orElseThrow(() -> new RuntimeException("not found"));
            EventMapper.INSTANCE.updateCustomerFromDto(eventDto, persistedEvent);
            repository.save(persistedEvent);
            template.convertAndSend(EXCHANGE, UPDATE_EVENT_NOTIFICATION_ROUTING_KEY, eventDto);
        }
    }

    @Override
    public void deleteEvent(Long id) {
        repository.deleteById(id);
        template.convertAndSend(EXCHANGE, DELETE_EVENT_NOTIFICATION_ROUTING_KEY, id);
    }
}
