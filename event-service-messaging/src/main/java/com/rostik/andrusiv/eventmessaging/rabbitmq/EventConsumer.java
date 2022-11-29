package com.rostik.andrusiv.eventmessaging.rabbitmq;

import com.rostik.andrusiv.entity.EventDto;
import com.rostik.andrusiv.profile.RabbitMQProfile;
import com.rostik.andrusiv.eventserviceapi.EventServiceConsumer;
import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Component;

import static com.rostik.andrusiv.common.Constants.*;

@Component
@RabbitMQProfile
@Slf4j
public class EventConsumer implements EventServiceConsumer<EventDto> {

    @RabbitListener(id = "createEvent", queues = CREATE_EVENT_REQUEST_QUEUE)
    public void createEvent(EventDto eventDto) {
        log.info(String.format("RabbitListener got msg from queue %s. Created Event %s ", CREATE_EVENT_REQUEST_QUEUE, eventDto));
    }

    @RabbitListener(id = "updateEvent", queues = UPDATE_EVENT_REQUEST_QUEUE)
    public void updateEvent(EventDto eventDto) {
        log.info(String.format("RabbitListener got msg from queue %s. Updated Event %s ", UPDATE_EVENT_REQUEST_QUEUE, eventDto));
    }

    @RabbitListener(id = "deleteEvent", queues = DELETE_EVENT_REQUEST_QUEUE)
    public void deleteEvent(Long eventId) {
        log.info(String.format("RabbitListener got msg from queue %s. Deleted Event with id %s ", UPDATE_EVENT_REQUEST_QUEUE, eventId));
    }
}
