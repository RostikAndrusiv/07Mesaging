package com.rostik.andrusiv.mesaging.eventmessaging.activemq;

import com.rostik.andrusiv.mesaging.repository.EventRepository;
import com.rostik.andrusiv.mesaging.serviceapi.EventMessaging;
import com.rostik.andrusiv.mesaging.servicedto.entity.EventDto;
import com.rostik.andrusiv.mesaging.servicedto.entity.EventEntity;
import com.rostik.andrusiv.mesaging.util.EventMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Profile;
import org.springframework.jms.core.JmsTemplate;
import org.springframework.stereotype.Component;

import javax.jms.Queue;

@Component
@Profile("activemq")
public class EventProducer implements EventMessaging {

    @Autowired
    private EventRepository repository;

    @Autowired
    private JmsTemplate jmsTemplate;

    @Autowired
    private Queue createEventQueue;

    @Autowired
    private Queue updateEventQueue;

    @Autowired
    private Queue deleteEventQueue;

    @Override
    public void createEvent(EventDto eventDto) {
        repository.save(EventMapper.INSTANCE.dtoToEventModel(eventDto));
        jmsTemplate.convertAndSend(createEventQueue, eventDto);
    }

    @Override
    public void updateEvent(EventDto eventDto) {
        if (eventDto != null && eventDto.getEventId() != null) {
            EventEntity persistedEvent = repository.findById(eventDto.getEventId()).orElseThrow(() -> new RuntimeException("not found"));
            EventMapper.INSTANCE.updateCustomerFromDto(eventDto, persistedEvent);
            repository.save(persistedEvent);
            jmsTemplate.convertAndSend(updateEventQueue, eventDto);
        }
    }

    @Override
    public void deleteEvent(Long id) {
        repository.deleteById(id);
        jmsTemplate.convertAndSend(deleteEventQueue, id);
    }
}
