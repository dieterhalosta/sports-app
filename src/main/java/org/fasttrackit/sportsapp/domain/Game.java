//package org.fasttrackit.sportsapp.domain;
//
//
//
//
//import javax.persistence.*;
//import java.util.HashSet;
//import java.util.Set;
//
//@Entity
//public class Game {
//
//    //This class will have an event and users
//    //A Game has one EVENT -> OneToOne
//    //A Game can have multiple USERS and one USER can have multiple GAMES -> ManyToMany
//
//    @Id
//    private long id;
//
//    @OneToOne(fetch = FetchType.LAZY)
//    @MapsId
//    private Event event;
//
//    @ManyToMany(cascade = CascadeType.MERGE)
//    @JoinTable(name = "game_user",
//    joinColumns = @JoinColumn(name = "game_id"),
//    inverseJoinColumns = @JoinColumn(name = "user_id"))
//    private Set<User> users = new HashSet<>();
//
//    public void addUser(User user){
//        users.add(user);
//
//        user.getGames().add(this);
//    }
//
//    public void removeUser(User user){
//        users.remove(user);
//
//        user.getGames().remove(this);
//    }
//
//    public long getId() {
//        return id;
//    }
//
//    public void setId(long id) {
//        this.id = id;
//    }
//
//    public Event getEvent() {
//        return event;
//    }
//
//    public void setEvent(Event event) {
//        this.event = event;
//    }
//
//    @Override
//    public String toString() {
//        return "Game{" +
//                "id=" + id +
//                '}';
//    }
//}
