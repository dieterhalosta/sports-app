package org.fasttrackit.sportsapp.persistance;


import org.fasttrackit.sportsapp.domain.Game;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;


public interface GameRepository extends JpaRepository<Game, Long> {

    @Query("SELECT user.firstName, user.lastName, event.description, event.date, event.imageUrl, event.location, event.name, event.participants FROM User user JOIN Game game ON user.id JOIN Event event ON game.id=event.id WHERE user.id = :activeUserId")
//            "(:activeUserId IS null or user.id LIKE %:activeUserId%)")
    Page<Game> getUserEvents (long activeUserId, Pageable pageable);

}
