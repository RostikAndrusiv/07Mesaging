package com.rostik.andrusiv.mesaging.serviceapi;

import com.rostik.andrusiv.mesaging.servicedto.entity.EventDto;

public interface EventMessaging {
    void createEvent(EventDto eventDto);

    void updateEvent(EventDto eventDto);

    void deleteEvent(Long id);
}

