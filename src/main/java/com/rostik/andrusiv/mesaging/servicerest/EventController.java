package com.rostik.andrusiv.mesaging.servicerest;

import com.rostik.andrusiv.mesaging.serviceapi.EventService;
import com.rostik.andrusiv.mesaging.servicedto.entity.EventDto;
import com.rostik.andrusiv.mesaging.servicerest.api.EventApi;
import com.rostik.andrusiv.mesaging.servicerest.assembler.EventAssembler;
import com.rostik.andrusiv.mesaging.servicerest.model.EventModel;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@Slf4j
public class EventController implements EventApi {

    @Autowired
    EventService service;

    @Autowired
    EventAssembler assembler;

//    @PostMapping("/event")
    public EventModel createEvent(EventDto eventDto){
        return assembler.toModel(service.createEvent(eventDto));
    }

//    @PutMapping("/event/{id}")
    public EventModel updateEvent(Long id, EventDto eventDto){
        service.updateEvent(id, eventDto);
        return assembler.toModel(service.updateEvent(id, eventDto));
    }

//    @DeleteMapping("/event/{id}")
    public ResponseEntity<Void> deleteEvent(Long id){
        service.deleteEvent(id);
        return ResponseEntity.noContent().build();
    }

//    @GetMapping("/event/{id}")
    public EventModel getEvent(Long id){
        return assembler.toModel(service.getEvent(id));
    }

//    @GetMapping("/event")
    public List<EventModel> getAllEvents(){
        return service.getAllEvents().stream()
                .map(eventDto -> assembler.toModel(eventDto))
                .collect(Collectors.toList());
    }
}
