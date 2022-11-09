package com.rostik.andrusiv.mesaging.eventmessaging.kafka;

import com.google.gson.Gson;
import com.rostik.andrusiv.mesaging.repository.EventRepository;
import com.rostik.andrusiv.mesaging.serviceapi.EventMessaging;
import com.rostik.andrusiv.mesaging.servicedto.entity.EventDto;
import com.rostik.andrusiv.mesaging.servicedto.entity.EventEntity;
import com.rostik.andrusiv.mesaging.util.EventMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Profile;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Component;

@Component
@Profile("kafka")
public class EventProducer implements EventMessaging {

    @Autowired
    private KafkaTemplate<String, String> kafkaTemplate;

    @Autowired
    private EventRepository repository;

    private final Gson gson = new Gson();

    @Override
    public void createEvent(EventDto eventDto) {
        repository.save(EventMapper.INSTANCE.dtoToEventModel(eventDto));
        kafkaTemplate.send("createEventTopic", gson.toJson(eventDto));
    }

    @Override
    public void updateEvent(EventDto eventDto) {
        if (eventDto != null && eventDto.getEventId() != null) {
            EventEntity persistedEvent = repository.findById(eventDto.getEventId()).orElseThrow(() -> new RuntimeException("not found"));
            EventMapper.INSTANCE.updateCustomerFromDto(eventDto, persistedEvent);
            repository.save(persistedEvent);
            kafkaTemplate.send("updateEventTopic", gson.toJson(eventDto));
        }
    }

    @Override
    public void deleteEvent(Long id) {
        repository.deleteById(id);
        kafkaTemplate.send("updateEventTopic", String.valueOf(id));
    }
}
