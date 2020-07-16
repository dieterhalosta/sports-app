package org.fasttrackit.sportsapp.web;

import org.fasttrackit.sportsapp.service.GameService;
import org.fasttrackit.sportsapp.transfer.game.AddUsersToGameRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@CrossOrigin
@RestController
@RequestMapping("/games")
public class GameController {

    private final GameService gameService;

    @Autowired
    public GameController(GameService gameService) {
        this.gameService = gameService;
    }

    @PutMapping("/{eventId}")
    public ResponseEntity<Void> addUsersToGame(@PathVariable long eventId, @Valid @RequestBody AddUsersToGameRequest request){
        gameService.addUsersToGame(eventId, request);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

}
