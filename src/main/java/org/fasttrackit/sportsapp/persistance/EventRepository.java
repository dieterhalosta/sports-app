package org.fasttrackit.sportsapp.persistance;

import org.fasttrackit.sportsapp.domain.Event;
import org.springframework.data.jpa.repository.JpaRepository;

public interface EventRepository extends JpaRepository<Event, Long> {
}
