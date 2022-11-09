package com.rostik.andrusiv.mesaging.serviceapi;

import com.rostik.andrusiv.mesaging.servicedto.entity.EventDto;

import java.util.List;

public interface EventService {
    EventDto createEvent(EventDto eventDto);

    EventDto updateEvent(Long id, EventDto eventDto);

    EventDto getEvent(Long id);

    EventDto deleteEvent(Long id);

    List<EventDto> getAllEvents();

}
