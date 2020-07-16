package org.fasttrackit.sportsapp.persistance;

import org.fasttrackit.sportsapp.domain.Game;
import org.springframework.data.jpa.repository.JpaRepository;

public interface GameRepository extends JpaRepository<Game, Long> {
}
