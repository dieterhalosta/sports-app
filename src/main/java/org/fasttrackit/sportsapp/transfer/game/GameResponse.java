package org.fasttrackit.sportsapp.transfer.game;

import org.fasttrackit.sportsapp.domain.User;

import java.util.List;

public class GameResponse {

    private long id;
    private List<UserInGameResponse> users;
    private EventInGameResponse event;
    private User mainUser;

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public List<UserInGameResponse> getUsers() {
        return users;
    }

    public void setUsers(List<UserInGameResponse> users) {
        this.users = users;
    }

    public EventInGameResponse getEvent() {
        return event;
    }

    public void setEvent(EventInGameResponse event) {
        this.event = event;
    }

    public User getMainUser() {
        return mainUser;
    }

    public void setMainUser(User mainUser) {
        this.mainUser = mainUser;
    }
}
