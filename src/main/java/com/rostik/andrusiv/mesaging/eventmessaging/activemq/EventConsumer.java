package com.rostik.andrusiv.mesaging.eventmessaging.activemq;

import com.rostik.andrusiv.mesaging.eventmessaging.Consumer;
import com.rostik.andrusiv.mesaging.servicedto.entity.EventDto;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.annotation.Profile;
import org.springframework.jms.annotation.JmsListener;
import org.springframework.stereotype.Component;

@Component
@Profile("activemq")
@Slf4j
public class EventConsumer implements Consumer {

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
