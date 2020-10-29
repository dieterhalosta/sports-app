package org.fasttrackit.sportsapp.transfer.user;

import org.fasttrackit.sportsapp.domain.User;
import org.fasttrackit.sportsapp.transfer.game.EventInGameResponse;

import java.util.List;

public class GetUserGameResponse {

    private long id;
    private List<EventInGameResponse> events;
    private User user;

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public List<EventInGameResponse> getEvents() {
        return events;
    }

    public void setEvents(List<EventInGameResponse> events) {
        this.events = events;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    @Override
    public String toString() {
        return "GetUserGameResponse{" +
                "id=" + id +
                ", event=" + events +
                ", mainUser=" + user +
                '}';
    }
}
