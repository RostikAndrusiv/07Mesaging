package com.rostik.andrusiv.mesaging.servicerest.assembler;

import com.rostik.andrusiv.mesaging.servicedto.entity.EventDto;
import com.rostik.andrusiv.mesaging.servicerest.EventController;
import com.rostik.andrusiv.mesaging.servicerest.model.EventModel;
import org.springframework.hateoas.Link;
import org.springframework.hateoas.server.mvc.RepresentationModelAssemblerSupport;
import org.springframework.stereotype.Component;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;

@Component
public class EventAssembler extends RepresentationModelAssemblerSupport<EventDto, EventModel> {

    public static final String GET_REL = "get_event";
    public static final String GET_ALL_REL = "get_all_events";
    public static final String CREATE_REL = "create_event";
    public static final String UPDATE_REL = "update_event";
    public static final String DELETE_REL = "delete_event";

    public EventAssembler() {
        super(EventController.class, EventModel.class);
    }

    @Override
    public EventModel toModel(EventDto entity) {
        EventModel eventModel = new EventModel(); //FIXME exception if entity in constructor
        eventModel.setEventDto(entity);

        Link get = linkTo(methodOn(EventController.class).getEvent(entity.getEventId())).withRel(GET_REL);

        Link getAll = linkTo(methodOn(EventController.class).getAllEvents()).withRel(GET_ALL_REL);

        Link create = linkTo(methodOn(EventController.class).createEvent(entity)).withRel(CREATE_REL);

        Link update = linkTo(methodOn(EventController.class).updateEvent(entity.getEventId(), entity)).withRel(UPDATE_REL);

        Link delete = linkTo(methodOn(EventController.class).deleteEvent(entity.getEventId())).withRel(DELETE_REL);

        eventModel.add(get, getAll, create, update, delete);

        return eventModel;
    }
}
