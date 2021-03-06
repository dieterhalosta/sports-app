package org.fasttrackit.sportsapp.service;



import org.fasttrackit.sportsapp.domain.Event;
import org.fasttrackit.sportsapp.domain.Game;
import org.fasttrackit.sportsapp.domain.User;
import org.fasttrackit.sportsapp.exception.ResourceNotFoundException;
import org.fasttrackit.sportsapp.persistance.UserRepository;
import org.fasttrackit.sportsapp.transfer.game.EventInGameResponse;
import org.fasttrackit.sportsapp.transfer.game.GameResponse;
import org.fasttrackit.sportsapp.transfer.game.UserInGameResponse;
import org.fasttrackit.sportsapp.transfer.user.CreateUserRequest;
import org.fasttrackit.sportsapp.transfer.user.GetUserGameResponse;
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
public class UserService {
    private static final Logger LOGGER = LoggerFactory.getLogger(UserService.class);

    private final UserRepository userRepository;


    @Autowired
    public UserService(UserRepository userRepository) {
        this.userRepository = userRepository;

    }

    public UserResponse createUser (CreateUserRequest request){
        LOGGER.info("Creating user {}", request);

        User user = new User();
        user.setRole(request.getRole().name());
        user.setLastName(request.getLastName());
        user.setFirstName(request.getFirstName());
        user.setPhotoUrl(request.getPhotoUrl());
        user.setPhoneNumber(request.getPhoneNumber());
        user.setEmail(request.getEmail());

        User savedUser = userRepository.save(user);

        return mapUserResponse(savedUser);
    }

    @Transactional
    public UserResponse getUser(long id) {
        LOGGER.info("Getting user {}", id);

        User user = userRepository.findById(id).orElseThrow(() -> new ResourceNotFoundException("User " + id + " not found"));

        return mapUserResponse(user);
    }

    public User getSimpleUser(long id){
        LOGGER.info("Getting user {}", id);

        return userRepository.findById(id).orElseThrow(() -> new ResourceNotFoundException("User " + id + " not found"));
    }

    @Transactional
    public Page<UserResponse> getUsers (GetUserRequest request, Pageable pageable){
        Page<User> page = userRepository.findByOptionalCriteria(request.getPartialFirstName(), request.getPartialLastName(), pageable);

        List<UserResponse> userDtos = new ArrayList<>();

        for(User user : page.getContent()){
            UserResponse userResponse = mapUserResponse(user);

            userDtos.add(userResponse);
        }

        return new PageImpl<>(userDtos, pageable, page.getTotalElements());

    }


    @Transactional
    public UserResponse updateUser (long id, CreateUserRequest request){
        LOGGER.info("Updating user {}: {}", id, request);

        User user = getSimpleUser(id);

        user.setRole(request.getRole().name());
        user.setFirstName(request.getFirstName());
        user.setLastName(request.getLastName());
        user.setEmail(request.getEmail());
        user.setPhoneNumber(request.getPhoneNumber());
        user.setPhotoUrl(request.getPhotoUrl());

        User updateUser = userRepository.save(user);


        return mapUserResponse(updateUser);
    }


    public void deleteUser(long id){
        LOGGER.info("Deleting user {}", id);
        userRepository.deleteById(id);
    }

    @Transactional
    public Page<GetUserGameResponse> getUserGames(long userId, Pageable pageable){
        LOGGER.info("Getting games for user {}", userId);

        Page<User> page = userRepository.getUserEvents(userId, pageable);

        List<GetUserGameResponse> userGamesDto = new ArrayList<>();

        for(User user : page.getContent()){
            GetUserGameResponse getUserGameResponse = mapGameResponse(user);

            userGamesDto.add(getUserGameResponse);
        }

        return new PageImpl<>(userGamesDto, pageable, page.getTotalElements());
    }


    private UserResponse mapUserResponse(User user){
        UserResponse userResponse = new UserResponse();
        userResponse.setId(user.getId());
        userResponse.setRole(user.getRole());
        userResponse.setPhotoUrl(user.getPhotoUrl());
        userResponse.setPhoneNumber(user.getPhoneNumber());
        userResponse.setLastName(user.getLastName());
        userResponse.setFirstName(user.getFirstName());
        userResponse.setEmail(user.getEmail());

        return userResponse;
    }


    private GetUserGameResponse mapGameResponse(User user) {
        GetUserGameResponse gameResponse = new GetUserGameResponse();
        gameResponse.setId(user.getId());

        List<EventInGameResponse> eventDtos = new ArrayList<>();

        for(Game events : user.getGame()){
            EventInGameResponse eventResponse = new EventInGameResponse();
            eventResponse.setId(events.getId());
            eventResponse.setDescription(events.getEvent().getDescription());
            eventResponse.setDate(events.getEvent().getDate());
            eventResponse.setImageUrl(events.getEvent().getImageUrl());
            eventResponse.setLocation(events.getEvent().getLocation());
            eventResponse.setName(events.getEvent().getName());
            eventResponse.setParticipants(events.getEvent().getParticipants());

            eventDtos.add(eventResponse);
        }

        User mainUser = new User();
        mainUser.setId(user.getId());
        mainUser.setRole(user.getRole());
        mainUser.setPhotoUrl(user.getPhotoUrl());
        mainUser.setPhoneNumber(user.getPhoneNumber());
        mainUser.setLastName(user.getLastName());
        mainUser.setFirstName(user.getFirstName());
        mainUser.setEmail(user.getEmail());

        gameResponse.setEvents(eventDtos);
        gameResponse.setUser(mainUser);

        return gameResponse;
    }

}
