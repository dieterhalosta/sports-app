package org.fasttrackit.sportsapp;

import org.fasttrackit.sportsapp.domain.User;
import org.fasttrackit.sportsapp.domain.UserRole;
import org.fasttrackit.sportsapp.exception.ResourceNotFoundException;
import org.fasttrackit.sportsapp.service.UserService;
import org.fasttrackit.sportsapp.steps.UserTestSteps;
import org.fasttrackit.sportsapp.transfer.user.CreateUserRequest;
import org.fasttrackit.sportsapp.transfer.user.GetUserRequest;
import org.hamcrest.CoreMatchers;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;

import javax.validation.ConstraintViolationException;

import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.CoreMatchers.notNullValue;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.*;

@SpringBootTest
public class UserServiceIntegrationTests {

    @Autowired
    private UserService userService;

    @Autowired
    private UserTestSteps userTestSteps;

    @Test
    public void createUser_whenValidRequest_thenReturnCreatedUser(){
        userTestSteps.createUser();
    }

    @Test
    public void createUser_whenMissingMandatoryProperties_thenThrowException(){
        CreateUserRequest request = new CreateUserRequest();

        try {
            userService.createUser(request);
        } catch (Exception e){
            assertThat("Unexpected exception thrown.", e instanceof ConstraintViolationException);
        }

    }

    @Test
    public void getUser_whenExistingUser_thenReturnPageOfOneUser(){
        User user = userTestSteps.createUser();

        Page<User> userPage = userService.getUsers(new GetUserRequest(), PageRequest.of(0,1000));

        assertThat(userPage, CoreMatchers.notNullValue());
        assertThat(userPage.getTotalElements(), greaterThanOrEqualTo(1L));
        assertThat(userPage.getContent(), contains(user));
    }


    @Test
    public void updateUser_whenExistingUser_thenReturnUpdatedUser(){
        User user = userTestSteps.createUser();

        CreateUserRequest request = new CreateUserRequest();
        request.setRole(UserRole.PLAYER);
        request.setFirstName("UpdatedFirstName");
        request.setLastName("UpdateLastName");
        request.setEmail("updatetest@test.com");
        request.setPhoneNumber(44448741);


        User updatedUser = userService.updateUser(user.getId(), request);


        assertThat(updatedUser, CoreMatchers.notNullValue());
        assertThat(updatedUser.getId(), is(user.getId()));
        assertThat(updatedUser.getFirstName(), is(request.getFirstName()));
        assertThat(updatedUser.getLastName(), is(request.getLastName()));
        assertThat(updatedUser.getEmail(), is(request.getEmail()));
        assertThat(updatedUser.getPhoneNumber(), is(request.getPhoneNumber()));
        assertThat(updatedUser.getRole(), is(request.getRole().name()));
    }

    @Test
    public void deleteUser_whenExistingUser_thenReturnNotExistingAnymore(){
        User user = userTestSteps.createUser();

        userService.deleteUser(user.getId());

        Assertions.assertThrows(ResourceNotFoundException.class, () -> userService.getUser(user.getId()));
    }

}
