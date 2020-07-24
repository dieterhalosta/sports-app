package org.fasttrackit.sportsapp.steps;

import org.fasttrackit.sportsapp.domain.UserRole;
import org.fasttrackit.sportsapp.service.UserService;
import org.fasttrackit.sportsapp.transfer.user.CreateUserRequest;
import org.fasttrackit.sportsapp.transfer.user.UserResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.CoreMatchers.notNullValue;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.greaterThan;

@Component
public class UserTestSteps {
    @Autowired
    private UserService userService;

    public UserResponse createUser(){
        CreateUserRequest request = new CreateUserRequest();
        request.setRole(UserRole.CREATOR);
        request.setFirstName("TestFirstName");
        request.setLastName("TestLastName");
        request.setEmail("test@test.com");
        request.setPhoneNumber(72588999);

        UserResponse user = userService.createUser(request);

        assertThat (user, notNullValue());
        assertThat(user.getId(), greaterThan(0L));
        assertThat(user.getRole(), is(request.getRole().name()));
        assertThat(user.getFirstName(), is(request.getFirstName()));
        assertThat(user.getLastName(), is(request.getLastName()));
        assertThat(user.getEmail(), is(request.getEmail()));
        assertThat(user.getPhoneNumber(), is(request.getPhoneNumber()));

        return user;
    }
}
