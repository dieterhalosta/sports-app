package org.fasttrackit.sportsapp.persistance;

import org.fasttrackit.sportsapp.domain.Game;
import org.fasttrackit.sportsapp.domain.User;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

public interface UserRepository extends JpaRepository<User, Long> {
    @Query(value = "SELECT user FROM User user WHERE (:partialFirstName IS null OR user.firstName LIKE %:partialFirstName%) AND (:partialLastName IS null OR user.lastName >= :partialLastName)")
    Page<User> findByOptionalCriteria(String partialFirstName, String partialLastName, Pageable pageable);

    @Query("SELECT user.firstName, user.lastName, event.description, event.date, event.imageUrl, event.location, event.name, event.participants FROM User user JOIN Game game ON user.id JOIN Event event ON game.id=event.id WHERE (:activeUserId IS null or user.id = :activeUserId)")
    Page<User> getUserEvents (long activeUserId, Pageable pageable);
}
