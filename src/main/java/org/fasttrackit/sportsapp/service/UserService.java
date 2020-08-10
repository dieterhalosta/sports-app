package org.fasttrackit.sportsapp.service;



import org.fasttrackit.sportsapp.domain.User;
import org.fasttrackit.sportsapp.exception.ResourceNotFoundException;
import org.fasttrackit.sportsapp.persistance.UserRepository;
import org.fasttrackit.sportsapp.transfer.user.CreateUserRequest;
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

        UserResponse user = getUser(id);

        user.setRole(request.getRole().name());
        user.setFirstName(request.getFirstName());
        user.setLastName(request.getLastName());
        user.setEmail(request.getEmail());
        user.setPhoneNumber(request.getPhoneNumber());
        user.setPhotoUrl(request.getPhotoUrl());

        User updateUser = mapBack(user);

        User save = userRepository.save(updateUser);

        return mapUserResponse(save);
    }


    public void deleteUser(long id){
        LOGGER.info("Deleting user {}", id);
        userRepository.deleteById(id);
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

    private User mapBack (UserResponse userResponse){
        User user = new User();
        user.setId(userResponse.getId());
        user.setRole(userResponse.getRole());
        user.setPhotoUrl(userResponse.getPhotoUrl());
        user.setPhoneNumber(userResponse.getPhoneNumber());
        user.setLastName(userResponse.getLastName());
        user.setFirstName(userResponse.getFirstName());
        user.setEmail(userResponse.getEmail());

        return user;

    }

}
