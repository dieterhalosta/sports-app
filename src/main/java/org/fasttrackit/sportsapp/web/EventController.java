package org.fasttrackit.sportsapp.web;


import org.fasttrackit.sportsapp.domain.Event;
import org.fasttrackit.sportsapp.service.EventService;
import org.fasttrackit.sportsapp.transfer.SaveEventRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@CrossOrigin
@RestController
@RequestMapping("/events")
public class EventController {

    private final EventService eventService;

    @Autowired
    public EventController(EventService eventService) {
        this.eventService = eventService;
    }

    @PostMapping
    public ResponseEntity<Event> createEvent(@Valid @RequestBody SaveEventRequest request){
        Event event = eventService.creatProduct(request);

        return new ResponseEntity<>(event, HttpStatus.CREATED);
    }

    @PutMapping("/{id}")
    public ResponseEntity<Event> updateEvent(@PathVariable long id, @Valid @RequestBody SaveEventRequest request){
        Event event = eventService.updateEvent(id, request);

        return new ResponseEntity<>(event, HttpStatus.OK);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Event> deleteEvent(@PathVariable long id){
        eventService.deleteEvent(id);

        return ResponseEntity.noContent().build();
    }


}
