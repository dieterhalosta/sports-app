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
import java.sql.Date;
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
        assertThat(response.getDate(), is(event.getDate()));
        assertThat(response.getDescription(), is(event.getDescription()));
        assertThat(response.getLocation(), is(event.getLocation()));
        assertThat(response.getParticipants(), is(event.getParticipants()));
    }

    @Test
    void getEvent_whenNonExistingEvent_thenThrowResourceNotFoundException(){
        Assertions.assertThrows(ResourceNotFoundException.class,
                () -> eventService.getEvent(0));
    }

    @Test
    void updateEvent_whenExistingEventAndValidRequest_thenReturnUpdatedEvent(){
        Event event = createEvent();

        SaveEventRequest request = new SaveEventRequest();
        request.setName(event.getName() + " for the IT Team");
        request.setDescription(event.getDescription() + " with the IT guys.");
        request.setLocation(event.getLocation() + "FastTrackIT yard.");
        request.setDate(LocalDate.ofEpochDay(2020-10-22));

        Event updatedEvent = eventService.updateEvent(event.getId(), request);

        assertThat(updatedEvent, notNullValue());
        assertThat(updatedEvent.getId(), is(event.getId()));
        assertThat(updatedEvent.getName(), is(request.getName()));
        assertThat(updatedEvent.getDescription(), is(request.getDescription()));
    }

    @Test
    void updateEvent_whenNonExistingEvent_thenThrowResourceNotFoundException(){

        SaveEventRequest request = new SaveEventRequest();
        request.setName("Rugby");
        request.setDescription("Rubgy match for the IT guys");
        request.setLocation("Baia Mare Stadium");
        request.setDate(LocalDate.of(2020, 07,22));
        Assertions.assertThrows(ResourceNotFoundException.class, () -> eventService.updateEvent(0, request ));
    }

    @Test
    void updateEvent_whenNotAValidRequest_thenThrowException(){

        Event event = createEvent();
        SaveEventRequest request = new SaveEventRequest();
        request.setName(event.getName() + " for the IT Team");
        request.setDescription(event.getDescription() + " with the IT guys.");

        try {
            eventService.updateEvent(event.getId(), request);
        } catch (Exception e) {
            assertThat("Unexpected exception thrown.", e instanceof ConstraintViolationException);
        }
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
        request.setDate(LocalDate.of(2020, 07,12));
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
