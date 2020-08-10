package org.fasttrackit.sportsapp.service;

import org.fasttrackit.sportsapp.domain.Event;
import org.fasttrackit.sportsapp.domain.Game;
import org.fasttrackit.sportsapp.domain.User;
import org.fasttrackit.sportsapp.exception.ResourceNotFoundException;
import org.fasttrackit.sportsapp.persistance.GameRepository;
import org.fasttrackit.sportsapp.transfer.event.GetEventsRequest;
import org.fasttrackit.sportsapp.transfer.game.AddUsersToGameRequest;
import org.fasttrackit.sportsapp.transfer.game.GameResponse;
import org.fasttrackit.sportsapp.transfer.game.UserInGameResponse;
import org.fasttrackit.sportsapp.transfer.user.GetUserRequest;
import org.fasttrackit.sportsapp.transfer.user.UserResponse;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.ArrayList;
import java.util.List;

@Service
public class GameService {

    private static final Logger LOGGER = LoggerFactory.getLogger(UserService.class);

    private final GameRepository gameRepository;
    private final EventService eventService;
    private final UserService userService;

    @Autowired
    public GameService(GameRepository gameRepository, EventService eventService, UserService userService) {
        this.gameRepository = gameRepository;
        this.eventService = eventService;
        this.userService = userService;
    }

    @Transactional
    public void addUsersToGame(long gameId, AddUsersToGameRequest request){
        LOGGER.info("Adding users to game {}: {}", gameId, request);

        Game game = gameRepository.findById(gameId).orElse(new Game());

        if(game.getEvent() == null){
            Event event = eventService.getEventById(gameId);

            game.setEvent(event);
        }

        for (Long userId : request.getUserIds()){
            User user = userService.getSimpleUser(userId);

            game.addUser(user);
        }

        gameRepository.save(game);
    }

    @Transactional
    public GameResponse getGame(long id){
        LOGGER.info("Retrieving game {}", id);

        Game game = gameRepository.findById(id).orElseThrow(() -> new ResourceNotFoundException("Cart " + id + " does not exist"));

        GameResponse gameResponse = new GameResponse();
        gameResponse.setId(game.getId());

        List<UserInGameResponse> userDtos = new ArrayList<>();

        for(User user : game.getUsers()){
            UserInGameResponse userResponse = new UserInGameResponse();
            userResponse.setId(user.getId());
            userResponse.setEmail(user.getEmail());
            userResponse.setFirstName(user.getFirstName());
            userResponse.setLastName(user.getLastName());
            userResponse.setPhoneNumber(user.getPhoneNumber());
            userResponse.setPhotoUrl(user.getPhotoUrl());
            userResponse.setRole(user.getRole());

            userDtos.add(userResponse);
        }

        gameResponse.setUsers(userDtos);
        return gameResponse;
    }



}
