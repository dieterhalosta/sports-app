package org.fasttrackit.sportsapp.web;


import org.fasttrackit.sportsapp.domain.User;
import org.fasttrackit.sportsapp.service.UserService;
import org.fasttrackit.sportsapp.transfer.user.CreateUserRequest;
import org.fasttrackit.sportsapp.transfer.user.GetUserRequest;
import org.fasttrackit.sportsapp.transfer.user.UserResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@CrossOrigin
@RestController
@RequestMapping("/users")
public class UserController {

    private final UserService userService;


    @Autowired
    public UserController(UserService userService) {
        this.userService = userService;
    }

    @PostMapping
    public ResponseEntity<UserResponse> createUser(@Valid @RequestBody CreateUserRequest request){
        UserResponse user = userService.createUser(request);

        return new ResponseEntity<>(user, HttpStatus.CREATED);
    }

    @GetMapping("/{id}")
    public ResponseEntity<UserResponse> getUser(@PathVariable long id){
        UserResponse user = userService.getUser(id);

        return new ResponseEntity<>(user, HttpStatus.OK);
    }

    @GetMapping
    public ResponseEntity<Page<UserResponse>> getUsers (@Valid GetUserRequest request, Pageable pageable){
        Page<UserResponse> users = userService.getUsers(request, pageable);

        return new ResponseEntity<>(users, HttpStatus.OK);

    }

    @PutMapping("/{id}")
    public ResponseEntity<UserResponse> updateUser(@PathVariable long id, @Valid @RequestBody CreateUserRequest request){
        UserResponse user = userService.updateUser(id, request);

        return new ResponseEntity<>(user, HttpStatus.OK);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteProduct(@PathVariable long id){
        userService.deleteUser(id);

        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }
}
