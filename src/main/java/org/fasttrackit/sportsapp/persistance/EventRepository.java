package org.fasttrackit.sportsapp.persistance;

import org.fasttrackit.sportsapp.domain.Event;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.time.LocalDate;

public interface EventRepository extends JpaRepository<Event, Long> {

        Page<Event> findByDateGreaterThanEqual(LocalDate date, Pageable page);

        @Query("SELECT event FROM Event event WHERE (:partialName IS null OR event.name LIKE %:partialName%) AND (:dateQuery IS null or event.date = :dateQuery) AND (:partialLocation IS null or event.location LIKE %:partialLocation%)")
        Page<Event> findByOptionalCriteria(String partialName, LocalDate dateQuery, String partialLocation, Pageable pageable);

}
