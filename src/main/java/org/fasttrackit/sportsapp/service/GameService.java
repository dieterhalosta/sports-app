package org.fasttrackit.sportsapp.service;

import org.fasttrackit.sportsapp.domain.Event;
import org.fasttrackit.sportsapp.domain.Game;
import org.fasttrackit.sportsapp.domain.User;
import org.fasttrackit.sportsapp.persistance.GameRepository;
import org.fasttrackit.sportsapp.transfer.game.AddUsersToGameRequest;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;

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
            User user = userService.getUser(userId);

            game.addUser(user);
        }

        gameRepository.save(game);
    }


}
