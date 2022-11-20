package com.rostik.andrusiv.eventmessaging.rabbitmq;

import com.rostik.andrusiv.entity.EventDto;
import com.rostik.andrusiv.eventmessaging.profile.RabbitMQProfile;
import com.rostik.andrusiv.eventserviceapi.EventServiceConsumer;
import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Component;

@Component
@RabbitMQProfile
@Slf4j
public class EventConsumer implements EventServiceConsumer {

    @RabbitListener(id = "createEvent", queues = RabbitMQConfig.CREATE_EVENT_REQUEST_QUEUE)
    public void createEvent(EventDto eventDto) {
        log.info(String.format("RabbitListener got msg from queue %s. Created Event %s ", RabbitMQConfig.CREATE_EVENT_REQUEST_QUEUE, eventDto));
    }

    @RabbitListener(id = "updateEvent", queues = RabbitMQConfig.UPDATE_EVENT_REQUEST_QUEUE)
    public void updateEvent(EventDto eventDto) {
        log.info(String.format("RabbitListener got msg from queue %s. Updated Event %s ", RabbitMQConfig.UPDATE_EVENT_REQUEST_QUEUE, eventDto));
    }

    @RabbitListener(id = "deleteEvent", queues = RabbitMQConfig.DELETE_EVENT_REQUEST_QUEUE)
    public void deleteEvent(Long eventId) {
        log.info(String.format("RabbitListener got msg from queue %s. Deleted Event with id %s ", RabbitMQConfig.UPDATE_EVENT_REQUEST_QUEUE, eventId));
    }
}
