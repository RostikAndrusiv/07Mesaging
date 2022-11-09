package com.rostik.andrusiv.mesaging.serviceimpl;

import com.rostik.andrusiv.mesaging.repository.EventRepository;
import com.rostik.andrusiv.mesaging.serviceapi.EventMessaging;
import com.rostik.andrusiv.mesaging.serviceapi.EventService;
import com.rostik.andrusiv.mesaging.servicedto.entity.EventDto;
import com.rostik.andrusiv.mesaging.servicedto.entity.EventEntity;
import com.rostik.andrusiv.mesaging.util.EventMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.stream.Collectors;

@Component
public class EventServiceImpl implements EventService {

    @Autowired
    private EventRepository repository;

    @Autowired(required = false)
    EventMessaging eventMessaging;

    @Override
    public EventDto createEvent(EventDto eventDto) {
        eventMessaging.createEvent(eventDto);
        return eventDto;
    }

    @Override
    public EventDto updateEvent(Long id, EventDto eventDto) {
        eventDto.setEventId(id);
        eventMessaging.updateEvent(eventDto);
        return eventDto;
    }

    @Override
    public EventDto deleteEvent(Long id) {
        EventEntity eventEntityToDelete = repository.findById(id).orElseThrow(()-> new RuntimeException("not found"));
        EventDto deletedEventDto = EventMapper.INSTANCE.EventModelToDto(eventEntityToDelete);
        eventMessaging.deleteEvent(id);
        return deletedEventDto;
    }

    @Override
    public EventDto getEvent(Long id) {
        EventEntity eventEntity = repository.findById(id).orElseThrow(()-> new RuntimeException("no event with id: " + id));
        return EventMapper.INSTANCE.EventModelToDto(eventEntity);
    }

    @Override
    public List<EventDto> getAllEvents() {
        return repository.findAll().stream().
                map(EventMapper.INSTANCE::EventModelToDto)
                .collect(Collectors.toList());
    }
}
