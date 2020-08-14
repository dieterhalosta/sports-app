package org.fasttrackit.sportsapp.unittests;

import org.fasttrackit.sportsapp.domain.Event;
import org.fasttrackit.sportsapp.domain.Game;
import org.fasttrackit.sportsapp.domain.User;
import org.fasttrackit.sportsapp.domain.UserRole;
import org.fasttrackit.sportsapp.persistance.GameRepository;
import org.fasttrackit.sportsapp.service.EventService;
import org.fasttrackit.sportsapp.service.GameService;
import org.fasttrackit.sportsapp.service.UserService;
import org.fasttrackit.sportsapp.transfer.game.AddUsersToGameRequest;
import org.fasttrackit.sportsapp.transfer.user.UserResponse;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.LocalDate;
import java.util.Collections;
import java.util.Optional;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class GameServiceUnitTests {

    private GameService gameService;

    @Mock
    private GameRepository gameRepository;
    @Mock
    private UserService userService;
    @Mock
    private EventService eventService;

    @BeforeEach
    public void setup(){gameService = new GameService(gameRepository, eventService, userService);}

    @Test
    public void addUsersToCart_whenNewEvent_thenNoErrorIsThrown(){
        when(gameRepository.findById(anyLong())).thenReturn(Optional.empty());

        Event event = new Event();
        event.setId(1L);
        event.setDate(LocalDate.ofEpochDay(2020-12-12));
        event.setDescription("TestUnitDescription");
        event.setLocation("TestLocation");
        event.setParticipants(4);
        event.setName("TestUnitName");
        event.setImageUrl("TestUnitImgURL");

        when(eventService.getEventById(anyLong())).thenReturn(event);


        User user = new User();
        user.setId(1L);
        user.setRole(UserRole.CREATOR.name());
        user.setPhotoUrl("UnitTestImgURL");
        user.setPhoneNumber("0722111555");
        user.setEmail("Unit.test@unitTest.com");
        user.setLastName("UnitTestLastName");
        user.setFirstName("UnitTestFirstName");


        when(userService.getSimpleUser(anyLong())).thenReturn(user);

        when(gameRepository.save(any(Game.class))).thenReturn(null);

        AddUsersToGameRequest request = new AddUsersToGameRequest();
        request.setUserIds(Collections.singletonList(user.getId()));

        gameService.addUsersToGame(event.getId(), request);

        verify(gameRepository).findById(anyLong());
        verify(eventService).getEventById(anyLong());
        verify(userService).getSimpleUser(anyLong());
        verify(gameRepository).save(any(Game.class));
    }
}
