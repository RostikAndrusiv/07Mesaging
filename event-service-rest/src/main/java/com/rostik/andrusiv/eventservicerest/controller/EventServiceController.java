package com.rostik.andrusiv.eventservicerest.controller;

import com.rostik.andrusiv.entity.EventDto;
import com.rostik.andrusiv.eventserviceapi.EventService;
import com.rostik.andrusiv.eventservicerest.api.EventApi;
import com.rostik.andrusiv.eventservicerest.assembler.EventAssembler;
import com.rostik.andrusiv.eventservicerest.model.EventModel;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@Slf4j
public class EventServiceController implements EventApi {

    @Autowired
    private EventService service;

    @Autowired
    private EventAssembler assembler;

    //    @PostMapping("/event")
    public EventModel createEvent(EventDto eventDto) {
        return assembler.toModel(service.createEvent(eventDto));
    }

    //    @PutMapping("/event/{id}")
    public EventModel updateEvent(Long id, EventDto eventDto) {
        service.updateEvent(id, eventDto);
        return assembler.toModel(service.updateEvent(id, eventDto));
    }

    //    @DeleteMapping("/event/{id}")
    public ResponseEntity<Void> deleteEvent(Long id) {
        service.deleteEvent(id);
        return ResponseEntity.noContent().build();
    }

    //    @GetMapping("/event/{id}")
    public EventModel getEvent(Long id) {
        return assembler.toModel(service.getEvent(id));
    }

    //    @GetMapping("/event")
    public List<EventModel> getAllEvents() {
        return service.getAllEvents().stream()
                .map(eventDto -> assembler.toModel(eventDto))
                .collect(Collectors.toList());
    }
}
