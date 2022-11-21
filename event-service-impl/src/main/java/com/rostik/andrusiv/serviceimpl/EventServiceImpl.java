package com.rostik.andrusiv.serviceimpl;

import com.rostik.andrusiv.entity.EventDto;
import com.rostik.andrusiv.entity.EventEntity;
import com.rostik.andrusiv.eventserviceapi.EventMessaging;
import com.rostik.andrusiv.eventserviceapi.EventService;
import com.rostik.andrusiv.repository.EventRepository;
import com.rostik.andrusiv.util.EventMapper;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@Slf4j
public class EventServiceImpl implements EventService {

    @Autowired
    private EventRepository eventRepository;

    @Autowired(required = false)
    EventMessaging eventMessaging;

    @Override
    public EventDto createEvent(EventDto eventDto) {
        EventEntity entity = eventRepository.save(EventMapper.INSTANCE.dtoToEventModel(eventDto));
        eventDto.setEventId(entity.getEventId());
        if (null != eventMessaging){
            eventMessaging.createEvent(eventDto);
        }
        return eventDto;
    }

    @Override
    public EventDto updateEvent(Long id, EventDto eventDto) {
        if (eventDto != null && eventDto.getEventId() != null) {
            eventDto.setEventId(id);
            EventEntity persistedEvent = eventRepository.findById(eventDto.getEventId()).orElseThrow(() -> new RuntimeException("not found"));
            EventMapper.INSTANCE.updateCustomerFromDto(eventDto, persistedEvent);
            eventRepository.save(persistedEvent);
        }
        if (null != eventMessaging){
            eventMessaging.updateEvent(eventDto);
        }
        return eventDto;
    }

    @Override
    public EventDto deleteEvent(Long id) {
        EventEntity eventEntityToDelete = eventRepository.findById(id).orElseThrow(() -> new RuntimeException("not found"));
        EventDto deletedEventDto = EventMapper.INSTANCE.EventModelToDto(eventEntityToDelete);
        eventRepository.deleteById(id);
        if (null != eventMessaging){
            eventMessaging.deleteEvent(id);
        }
        return deletedEventDto;
    }

    @Override
    public EventDto getEvent(Long id) {
        EventEntity eventEntity = eventRepository.findById(id).orElseThrow(() -> new RuntimeException("no event with id: " + id));
        return EventMapper.INSTANCE.EventModelToDto(eventEntity);
    }

    @Override
    public List<EventDto> getAllEvents() {
        return eventRepository.findAll().stream().
                map(EventMapper.INSTANCE::EventModelToDto)
                .collect(Collectors.toList());
    }
}
