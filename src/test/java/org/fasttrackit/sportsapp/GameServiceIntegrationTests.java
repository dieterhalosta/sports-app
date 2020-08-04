package org.fasttrackit.sportsapp;

import org.fasttrackit.sportsapp.domain.Event;
import org.fasttrackit.sportsapp.service.GameService;
import org.fasttrackit.sportsapp.steps.EventTestSteps;
import org.fasttrackit.sportsapp.steps.UserTestSteps;
import org.fasttrackit.sportsapp.transfer.game.AddUsersToGameRequest;
import org.fasttrackit.sportsapp.transfer.game.GameResponse;
import org.fasttrackit.sportsapp.transfer.user.UserResponse;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.context.ActiveProfiles;

import java.util.Collections;

import static org.hamcrest.CoreMatchers.*;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.hasSize;


@SpringBootTest
@ActiveProfiles("test")
@DirtiesContext(classMode = DirtiesContext.ClassMode.BEFORE_EACH_TEST_METHOD)
public class GameServiceIntegrationTests {

    @Autowired
    private GameService gameService;

    @Autowired
    private EventTestSteps eventTestSteps;

    @Autowired
    private UserTestSteps userTestSteps;


    @Test
    public void testAddUsersToGame_whenNewUser_thenCreateCartForUser(){
        Event event = eventTestSteps.createEvent();

        UserResponse user = userTestSteps.createUser();

        AddUsersToGameRequest request = new AddUsersToGameRequest();

        request.setUserIds(Collections.singletonList(user.getId()));

        gameService.addUsersToGame(event.getId(), request);

        GameResponse gameResponse = gameService.getGame(event.getId());

        assertThat(gameResponse, notNullValue());
        assertThat(gameResponse.getId(), is(event.getId()));
        assertThat(gameResponse.getUsers(), notNullValue());
        assertThat(gameResponse.getUsers(), hasSize(1));
        assertThat(gameResponse.getUsers().get(0), notNullValue());
        assertThat(gameResponse.getUsers().get(0).getId(), is(user.getId()));
        assertThat(gameResponse.getUsers().get(0).getFirstName(), is(user.getFirstName()));
        assertThat(gameResponse.getUsers().get(0).getLastName(), is(user.getLastName()));
        assertThat(gameResponse.getUsers().get(0).getEmail(), is(user.getEmail()));
        assertThat(gameResponse.getUsers().get(0).getPhoneNumber(), is(user.getPhoneNumber()));
        assertThat(gameResponse.getUsers().get(0).getPhotoUrl(), is(user.getPhotoUrl()));
        assertThat(gameResponse.getUsers().get(0).getRole(), is(user.getRole()));

    }

}
