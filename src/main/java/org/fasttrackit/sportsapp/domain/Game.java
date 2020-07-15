package org.fasttrackit.sportsapp.domain;


import javax.persistence.*;

@Entity
public class Game {

    //This class will have an event and users
    //A Game has one EVENT
    //A Game can have multiple USERS

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
