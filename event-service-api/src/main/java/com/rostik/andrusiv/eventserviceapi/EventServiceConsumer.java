package com.rostik.andrusiv.eventserviceapi;

import java.io.Serializable;

public interface EventServiceConsumer<E extends Serializable> {

    void createEvent(E eventDto);

    void updateEvent(E eventDto);

    void deleteEvent(Long eventID);
}
