package com.rostik.andrusiv.mesaging.servicerest.api;

import com.rostik.andrusiv.mesaging.servicedto.entity.EventDto;
import com.rostik.andrusiv.mesaging.servicerest.model.EventModel;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Api(tags = "Event management API")
@RequestMapping("/api/v1/event")
public interface EventApi {
    @ApiImplicitParams({
            @ApiImplicitParam(name = "id", paramType = "path", required = true, value = "Event id"),
    })
    @ApiOperation("Get event")
    @ResponseStatus(HttpStatus.OK)
    @GetMapping(value = "{id}")
    EventModel getEvent(@PathVariable Long id);

    @ApiOperation("Get all events")
    @ResponseStatus(HttpStatus.OK)
    @GetMapping
    List<EventModel> getAllEvents();

    @ApiOperation("Create event")
    @ResponseStatus(HttpStatus.CREATED)
    @PostMapping
    EventModel createEvent(@RequestBody EventDto eventDto);

    @ApiImplicitParams({
            @ApiImplicitParam(name = "id", paramType = "path", required = true, value = "Event id"),
    })
    @ApiOperation("Update event")
    @ResponseStatus(HttpStatus.OK)
    @PutMapping("/{id}")
    EventModel updateEvent(@PathVariable Long id, @RequestBody EventDto eventDto);

    @ApiImplicitParams({
            @ApiImplicitParam(name = "id", paramType = "path", required = true, value = "Event id"),
    })
    @ApiOperation("Delete event")
    @DeleteMapping(value = "/{id}")
    ResponseEntity<Void> deleteEvent(@PathVariable Long id);
}
