package org.fasttrackit.sportsapp.steps;

import org.fasttrackit.sportsapp.domain.Event;
import org.fasttrackit.sportsapp.service.EventService;
import org.fasttrackit.sportsapp.transfer.event.SaveEventRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.time.LocalDate;

import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.CoreMatchers.notNullValue;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.greaterThan;

@Component
public class EventTestSteps {

    @Autowired
    private EventService eventService;

    public Event createEvent() {
        SaveEventRequest request = new SaveEventRequest();
        request.setName("Carting");
        request.setDate(LocalDate.of(2020, 10,10));
        request.setDescription("A short game");
        request.setLocation("Cluj");
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
