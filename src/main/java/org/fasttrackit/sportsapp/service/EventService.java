package org.fasttrackit.sportsapp.service;

import org.fasttrackit.sportsapp.domain.Event;
import org.fasttrackit.sportsapp.exception.ResourceNotFoundException;
import org.fasttrackit.sportsapp.persistance.EventRepository;
import org.fasttrackit.sportsapp.transfer.SaveEventRequest;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class EventService {

    private static final Logger LOGGER = LoggerFactory.getLogger(EventService.class);

    private final EventRepository eventRepository;

    @Autowired
    public EventService(EventRepository eventRepository) {
        this.eventRepository = eventRepository;
    }

    public Event creatProduct(SaveEventRequest request){
//        System.out.println("Creating Product: " + request);

        LOGGER.info("Creating product {}", request);

        Event event = new Event();
//        event.setName(request.getName());
//        event.setDescription(request.getDescription());
//        event.setParticipants(request.getParticipants());
//        event.setDate(request.getDate());
//        event.setLocation(request.getLocation());
//        event.setImageUrl(request.getImageUrl());
//
        BeanUtils.copyProperties(request, event);

        return eventRepository.save(event);
    }

    public Event getEvent(long id){
        LOGGER.info("Retrieving event {}", id);

        return eventRepository.findById(id).orElseThrow(() -> new ResourceNotFoundException("Event " + id + " not found."));
    }

    public Event updateEvent(long id, SaveEventRequest request){
        LOGGER.info("Updating event {}: {}", id, request);

        Event event = getEvent(id);

        BeanUtils.copyProperties(request, event);

        return eventRepository.save(event);
    }

    public void deleteEvent(long id){
        LOGGER.info("Deleting product {}", id);
        eventRepository.deleteById(id);
    }

}
