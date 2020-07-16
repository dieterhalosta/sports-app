package org.fasttrackit.sportsapp;

import org.fasttrackit.sportsapp.domain.Event;
import org.fasttrackit.sportsapp.exception.ResourceNotFoundException;
import org.fasttrackit.sportsapp.service.EventService;
import org.fasttrackit.sportsapp.steps.EventTestSteps;
import org.fasttrackit.sportsapp.transfer.event.SaveEventRequest;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import javax.validation.ConstraintViolationException;
import java.time.LocalDate;

import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.CoreMatchers.notNullValue;
import static org.hamcrest.MatcherAssert.assertThat;

@SpringBootTest
class EventServiceIntegrationTests {

    @Autowired
    private EventService eventService;

    @Autowired
    private EventTestSteps eventTestSteps;

    @Test
    void createEvent_whenValidRequest_thenReturnCreatedProduct() {
        eventTestSteps.createEvent();
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
        Event event = eventTestSteps.createEvent();

        Event response = eventService.getEventById(event.getId());
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
                () -> eventService.getEventById(0));
    }

    @Test
    void updateEvent_whenExistingEventAndValidRequest_thenReturnUpdatedEvent(){
        Event event = eventTestSteps.createEvent();

        SaveEventRequest request = new SaveEventRequest();
        request.setName(event.getName() + " for the IT Team");
        request.setDescription(event.getDescription() + " with the IT guys.");
        request.setLocation(event.getLocation() + "FastTrackIT yard.");
        request.setDate(LocalDate.ofEpochDay(2020-10-22));
        request.setParticipants(event.getParticipants());

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
        request.setDate(LocalDate.of(2020, 7,22));
        Assertions.assertThrows(ResourceNotFoundException.class, () -> eventService.updateEvent(0, request ));
    }

    @Test
    void updateEvent_whenNotAValidRequest_thenThrowException(){

        Event event = eventTestSteps.createEvent();
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
        Event event = eventTestSteps.createEvent();
        eventService.deleteEvent(event.getId());
        Assertions.assertThrows(ResourceNotFoundException.class,
                () -> eventService.getEventById(event.getId()));
    }


}
