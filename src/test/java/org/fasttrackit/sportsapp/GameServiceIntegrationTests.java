package org.fasttrackit.sportsapp;

import org.fasttrackit.sportsapp.domain.Event;
import org.fasttrackit.sportsapp.domain.User;
import org.fasttrackit.sportsapp.service.GameService;
import org.fasttrackit.sportsapp.steps.EventTestSteps;
import org.fasttrackit.sportsapp.steps.UserTestSteps;
import org.fasttrackit.sportsapp.transfer.game.AddUsersToGameRequest;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.Collections;

@SpringBootTest
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

        User user = userTestSteps.createUser();

        AddUsersToGameRequest request = new AddUsersToGameRequest();

        request.setUserIds(Collections.singletonList(user.getId()));

        gameService.addUsersToGame(event.getId(), request);
    }

}
