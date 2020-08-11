package org.fasttrackit.sportsapp.web;


import org.fasttrackit.sportsapp.service.GameService;
import org.fasttrackit.sportsapp.transfer.game.AddUsersToGameRequest;
import org.fasttrackit.sportsapp.transfer.game.GameResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
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

    @GetMapping("/{eventId}")
    public ResponseEntity<GameResponse> getGame(@PathVariable long eventId){
        GameResponse game = gameService.getGame(eventId);
        return new ResponseEntity<>(game, HttpStatus.OK);
    }

    @GetMapping
    public ResponseEntity<Page<GameResponse>> getGames(Pageable pageable){
        Page<GameResponse> games = gameService.getGames(pageable);
        return new ResponseEntity<>(games, HttpStatus.OK);
    }

}
