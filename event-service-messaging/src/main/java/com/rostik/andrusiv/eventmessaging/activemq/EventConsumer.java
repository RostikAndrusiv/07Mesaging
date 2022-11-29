package com.rostik.andrusiv.eventmessaging.activemq;

import com.rostik.andrusiv.entity.EventDto;
import com.rostik.andrusiv.profile.ActiveMQProfile;
import com.rostik.andrusiv.eventserviceapi.EventServiceConsumer;
import lombok.extern.slf4j.Slf4j;
import org.springframework.jms.annotation.JmsListener;
import org.springframework.stereotype.Component;

import static com.rostik.andrusiv.common.Constants.*;

@Component
@ActiveMQProfile
@Slf4j
public class EventConsumer implements EventServiceConsumer<EventDto> {

    @JmsListener(destination = CREATE_EVENT_REQUEST_QUEUE)
    public void createEvent(EventDto eventDto) {
        log.info(String.format("JMSListener got msg from queue create-event-request-queue. Created Event %s ", eventDto));
    }

    @JmsListener(destination = UPDATE_EVENT_REQUEST_QUEUE)
    public void updateEvent(EventDto eventDto) {
        log.info(String.format("JMSListener got msg from queue update-event-request-queue. Updated Event %s ", eventDto));
    }

    @JmsListener(destination = DELETE_EVENT_REQUEST_QUEUE)
    public void deleteEvent(Long eventId) {
        log.info(String.format("JMSListener got msg from queue delete-event-request-queue. Deleted Event with id %s ", eventId));
    }
}
