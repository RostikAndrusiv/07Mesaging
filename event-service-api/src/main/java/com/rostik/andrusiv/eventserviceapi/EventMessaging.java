package com.rostik.andrusiv.eventserviceapi;

import com.rostik.andrusiv.entity.EventDto;

public interface EventMessaging {

    void createEvent(EventDto eventDto);

    void updateEvent(EventDto eventDto);

    void deleteEvent(Long id);
}

