package org.fasttrackit.sportsapp.domain;

import javax.persistence.*;

@Entity
public class Game {

    //OneToOne - Event - A game can only have one event and an Event can only be in one Game
    //ManyToMany - User - A game can have multiple users and a user can have multiple games.

    @Id
    private long id;

    @OneToOne(fetch = FetchType.LAZY)
    @MapsId
    private Event event;

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public Event getEvent() {
        return event;
    }

    public void setEvent(Event event) {
        this.event = event;
    }

    @Override
    public String toString() {
        return "Game{" +
                "id=" + id +
                '}';
    }
}
