package com.rostik.andrusiv.mesaging.servicerest.model;

import com.fasterxml.jackson.annotation.JsonUnwrapped;
import com.rostik.andrusiv.mesaging.servicedto.entity.EventDto;
import lombok.*;
import org.springframework.hateoas.RepresentationModel;

@NoArgsConstructor
@AllArgsConstructor
public class EventModel extends RepresentationModel<EventModel> {

    public EventDto getEventDto() {
        return eventDto;
    }

    public void setEventDto(EventDto eventDto) {
        this.eventDto = eventDto;
    }

    @JsonUnwrapped
    private EventDto eventDto;
}