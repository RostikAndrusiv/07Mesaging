package com.rostik.andrusiv.eventmessaging.activemq;

import com.rostik.andrusiv.entity.EventDto;
import com.rostik.andrusiv.eventmessaging.profile.ActiveMQProfile;
import com.rostik.andrusiv.eventserviceapi.EventServiceConsumer;
import lombok.extern.slf4j.Slf4j;
import org.springframework.jms.annotation.JmsListener;
import org.springframework.stereotype.Component;

@Component
@ActiveMQProfile
@Slf4j
public class EventConsumer implements EventServiceConsumer {

    @JmsListener(destination = "create-event-request")
    public void createEvent(EventDto eventDto) {
        log.info(String.format("JMSListener got msg from queue create-event-request. Created Event %s ", eventDto));
    }

    @JmsListener(destination = "update-event-request")
    public void updateEvent(EventDto eventDto) {
        log.info(String.format("JMSListener got msg from queue update-event-request. Updated Event %s ", eventDto));
    }

    @JmsListener(destination = "delete-event-request")
    public void deleteEvent(Long eventId) {
        log.info(String.format("JMSListener got msg from queue delete-event-request. Deleted Event with id %s ", eventId));
    }
}
