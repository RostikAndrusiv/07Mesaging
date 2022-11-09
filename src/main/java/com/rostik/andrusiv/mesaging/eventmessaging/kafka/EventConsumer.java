package com.rostik.andrusiv.mesaging.eventmessaging.kafka;

import com.rostik.andrusiv.mesaging.eventmessaging.Consumer;
import com.rostik.andrusiv.mesaging.servicedto.entity.EventDto;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.annotation.Profile;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Component;

import static com.rostik.andrusiv.mesaging.eventmessaging.kafka.KafkaTopicConfig.*;

@Component
@Profile("kafka")
@Slf4j
public class EventConsumer implements Consumer {

    @KafkaListener(topics = CREATE_EVENT_TOPIC,
            groupId = "groupId")
    public void createEvent(String msg) {
        log.info(String.format("KafkaListener got msg from topic %s. Created Event %s ", CREATE_EVENT_TOPIC, msg));
    }

    @KafkaListener(topics = UPDATE_EVENT_TOPIC,
            groupId = "groupId")
    public void updateEvent(EventDto eventDto) {
        log.info(String.format("KafkaListener got msg from topic %s. Updated Event %s ", UPDATE_EVENT_TOPIC, eventDto));
    }

    @KafkaListener(topics = DELETE_EVENT_TOPIC,
            groupId = "groupId")
    public void deleteEvent(Long eventId) {
        log.info(String.format("KafkaListener got msg from topic %s. Deleted Event with id %s ", DELETE_EVENT_TOPIC, eventId));
    }
}