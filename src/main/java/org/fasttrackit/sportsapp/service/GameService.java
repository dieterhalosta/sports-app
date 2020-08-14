package org.fasttrackit.sportsapp.service;

import org.fasttrackit.sportsapp.domain.Event;
import org.fasttrackit.sportsapp.domain.Game;
import org.fasttrackit.sportsapp.domain.User;
import org.fasttrackit.sportsapp.exception.ResourceNotFoundException;
import org.fasttrackit.sportsapp.persistance.GameRepository;

import org.fasttrackit.sportsapp.transfer.game.*;

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

    public void addEventToGame (AddEventToGameRequest request) {
        LOGGER.info("Adding event to game {}", request);

        Game game = new Game();


    }

    @Transactional
    public GameResponse getGame(long id){
        LOGGER.info("Retrieving game {}", id);

        Game game = gameRepository.findById(id).orElseThrow(() -> new ResourceNotFoundException("Game " + id + " does not exist"));

        return mapGameResponse(game);
    }


    private GameResponse mapGameResponse(Game game) {
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



        EventInGameResponse event = new EventInGameResponse();
        event.setId(game.getId());
        event.setDate(game.getEvent().getDate());
        event.setDescription(game.getEvent().getDescription());
        event.setName(game.getEvent().getName());
        event.setImageUrl(game.getEvent().getImageUrl());
        event.setParticipants(game.getEvent().getParticipants());
        event.setLocation(game.getEvent().getLocation());

        gameResponse.setUsers(userDtos);
        gameResponse.setEvent(event);


        if (userDtos.get(0) != null){
            User mainUser = new User();
            mainUser.setId(userDtos.get(0).getId());
            mainUser.setFirstName(userDtos.get(0).getFirstName());
            mainUser.setLastName(userDtos.get(0).getLastName());
            mainUser.setEmail(userDtos.get(0).getEmail());
            mainUser.setPhoneNumber(userDtos.get(0).getPhoneNumber());
            mainUser.setPhotoUrl(userDtos.get(0).getPhotoUrl());
            mainUser.setRole(userDtos.get(0).getRole());

            gameResponse.setMainUser(mainUser);
        }

        return gameResponse;
    }

    @Transactional
    public Page<GameResponse> getGames (Pageable pageable){
        Page<Game> page = gameRepository.findAll(pageable);

        List<GameResponse> gameDtos = new ArrayList<>();

        for(Game game : page.getContent()){
            GameResponse gameResponse = mapGameResponse(game);

            gameDtos.add(gameResponse);
        }

        return new PageImpl<>(gameDtos, pageable, page.getTotalElements());
    }


}
