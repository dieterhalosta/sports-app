package org.fasttrackit.sportsapp.web;


import org.fasttrackit.sportsapp.domain.Event;
import org.fasttrackit.sportsapp.service.EventService;
import org.fasttrackit.sportsapp.transfer.event.GetEventsRequest;
import org.fasttrackit.sportsapp.transfer.event.SaveEventRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
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

    @GetMapping("/{id}")
    public ResponseEntity<Event> getEvent(@PathVariable long id){
        Event eventById = eventService.getEventById(id);
        return new ResponseEntity<>(eventById, HttpStatus.OK);
    }

    @GetMapping
    public ResponseEntity<Page<Event>> getEvents(@Valid GetEventsRequest request, Pageable pageable){
        Page<Event> events = eventService.getEvents(request, pageable);
        return new ResponseEntity<>(events, HttpStatus.OK);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteEvent(@PathVariable long id){
        eventService.deleteEvent(id);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }


}
