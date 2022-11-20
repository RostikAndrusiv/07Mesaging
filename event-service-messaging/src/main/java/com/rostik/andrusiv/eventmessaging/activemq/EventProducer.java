package com.rostik.andrusiv.eventmessaging.activemq;

import com.rostik.andrusiv.entity.EventDto;
import com.rostik.andrusiv.eventmessaging.profile.ActiveMQProfile;
import com.rostik.andrusiv.eventserviceapi.EventMessaging;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jms.core.JmsTemplate;
import org.springframework.stereotype.Component;

import javax.jms.Queue;

@Component
@ActiveMQProfile
public class EventProducer implements EventMessaging {

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
        jmsTemplate.convertAndSend(createEventQueue, eventDto);
    }

    @Override
    public void updateEvent(EventDto eventDto) {
        jmsTemplate.convertAndSend(updateEventQueue, eventDto);
    }

    @Override
    public void deleteEvent(Long id) {
        jmsTemplate.convertAndSend(deleteEventQueue, id);
    }
}
