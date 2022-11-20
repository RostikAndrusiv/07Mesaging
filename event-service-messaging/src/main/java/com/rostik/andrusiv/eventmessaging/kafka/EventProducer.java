package com.rostik.andrusiv.eventmessaging.kafka;

import com.google.gson.Gson;
import com.rostik.andrusiv.entity.EventDto;
import com.rostik.andrusiv.eventmessaging.profile.KafkaProfile;
import com.rostik.andrusiv.eventserviceapi.EventMessaging;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Component;

@Component
@KafkaProfile
public class EventProducer implements EventMessaging {

    @Autowired
    private KafkaTemplate<String, String> kafkaTemplate;

    private final Gson gson = new Gson();

    @Override
    public void createEvent(EventDto eventDto) {
        kafkaTemplate.send("createEventTopic", gson.toJson(eventDto));
    }

    @Override
    public void updateEvent(EventDto eventDto) {
        kafkaTemplate.send("updateEventTopic", gson.toJson(eventDto));
    }

    @Override
    public void deleteEvent(Long id) {
        kafkaTemplate.send("updateEventTopic", String.valueOf(id));
    }
}
