package org.fasttrackit.sportsapp.service;

import org.fasttrackit.sportsapp.domain.Event;
import org.fasttrackit.sportsapp.persistance.EventRepository;
import org.fasttrackit.sportsapp.transfer.SaveEventRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class EventService {

    private final EventRepository eventRepository;

    @Autowired
    public EventService(EventRepository eventRepository) {
        this.eventRepository = eventRepository;
    }

    public Event creatProduct(SaveEventRequest request){
        System.out.println("Creating Product: " + request);

        Event event = new Event();
        event.setName(request.getName());
        event.setDescription(request.getDescription());
        event.setParticipants(request.getParticipants());
        event.setDate(request.getDate());
        event.setLocation(request.getLocation());
        event.setImageUrl(request.getImageUrl());

        return eventRepository.save(event);
    }
}
