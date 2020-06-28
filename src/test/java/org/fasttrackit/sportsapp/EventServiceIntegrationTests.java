package org.fasttrackit.sportsapp;

import org.fasttrackit.sportsapp.domain.Event;
import org.fasttrackit.sportsapp.service.EventService;
import org.fasttrackit.sportsapp.transfer.SaveEventRequest;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

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

}
