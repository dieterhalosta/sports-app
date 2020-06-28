package org.fasttrackit.sportsapp;

import org.fasttrackit.sportsapp.domain.Event;
import org.fasttrackit.sportsapp.exception.ResourceNotFoundException;
import org.fasttrackit.sportsapp.service.EventService;
import org.fasttrackit.sportsapp.transfer.SaveEventRequest;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.web.client.ResourceAccessException;

import javax.validation.ConstraintViolationException;
import java.time.LocalDate;

import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.CoreMatchers.notNullValue;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.greaterThan;

@SpringBootTest
class EventServiceIntegrationTests {

    @Autowired
    private EventService eventService;

    @Test
    void createEvent_whenValidRequest_thenReturnCreatedProduct() {
        createEvent();
    }

    @Test
    void createEvent_whenMissingMandatoryProperties_thenThrowException() {
        SaveEventRequest request = new SaveEventRequest();

        try {
            eventService.creatProduct(request);
        } catch (Exception e) {
            assertThat("Unexpected exception thrown.", e instanceof ConstraintViolationException);
        }
    }

    @Test
    void getEvent_whenExistingEvent_thenReturnEvent(){
        Event event = createEvent();

        Event response = eventService.getEvent(event.getId());
        assertThat(response, notNullValue());
        assertThat(response.getId(), is(event.getId()));
        assertThat(response.getName(), is(event.getName()));
//        assertThat(response.getDate(), is(event.getDate()));
        assertThat(response.getDescription(), is(event.getDescription()));
        assertThat(response.getLocation(), is(event.getLocation()));
        assertThat(response.getParticipants(), is(event.getParticipants()));
    }

    @Test
    void getEvent_whenNonExistingEvent_thenThrowResourceNotFoundException(){
        Assertions.assertThrows(ResourceNotFoundException.class,
                () -> eventService.getEvent(0));
    }


    //ToDo: add test for existing event, nonExisting event, ValidRequest, NotValidRequest

    @Test
    void updateEvent_whenValidRequest_thenReturnUpdatedEvent(){
        Event event = createEvent();

        SaveEventRequest request = new SaveEventRequest();
        request.setName(event.getName() + " for the IT Team");
        request.setDescription(event.getDescription() + " with the IT guys.");
        request.setLocation(event.getLocation() + "FastTrackIT yard.");
        request.setDate(event.getDate());

        Event updatedEvent = eventService.updateEvent(event.getId(), request);

        assertThat(updatedEvent, notNullValue());
        assertThat(updatedEvent.getId(), is(event.getId()));
        assertThat(updatedEvent.getName(), is(request.getName()));
        assertThat(updatedEvent.getDescription(), is(request.getDescription()));
    }

    @Test
    void deleteEvent_whenExistingEvent_thenEventDoesNotExistAnymore(){
        Event event = createEvent();
        eventService.deleteEvent(event.getId());
        Assertions.assertThrows(ResourceNotFoundException.class,
                () -> eventService.getEvent(event.getId()));
    }

    private Event createEvent() {
        SaveEventRequest request = new SaveEventRequest();
        request.setName("Basketball");
        request.setDate(LocalDate.now());
        request.setDescription("A short game");
        request.setLocation("LA");
        request.setParticipants(8);


        Event event = eventService.creatProduct(request);

        assertThat(event, notNullValue());
        assertThat(event.getId(), greaterThan(0L));
        assertThat(event.getName(), is(request.getName()));
        assertThat(event.getDate(), is(request.getDate()));
        assertThat(event.getDescription(), is(request.getDescription()));
        assertThat(event.getLocation(), is(request.getLocation()));
        assertThat(event.getParticipants(), is(request.getParticipants()));

        return event;
    }
}
