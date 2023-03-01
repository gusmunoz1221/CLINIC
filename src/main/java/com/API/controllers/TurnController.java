package com.API.controllers;

import com.API.Model.Dtos.TurnDto;
import com.API.Model.Dtos.TurnDtoResponse;
import com.API.services.TurnService;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import java.util.List;

@RestController
@RequestMapping("/turn")
public class TurnController {
    private final TurnService turnService;

    public TurnController(TurnService turnService) {
        this.turnService = turnService;
    }

    @PostMapping
    public ResponseEntity<TurnDtoResponse> setTurn(@RequestBody @Validated TurnDto turnDto) {
        return ResponseEntity.ok(turnService.addTurn(turnDto));
    }

    @GetMapping("/{id}")
    public ResponseEntity<TurnDtoResponse> getTurnById(@PathVariable(name = "id") int id) {
        return ResponseEntity.ok(turnService.findTurnById(id));
    }

    @GetMapping
        public ResponseEntity<List<TurnDtoResponse>> getAllTurns() {
        return ResponseEntity.ok(turnService.findAllTurns());
    }

    @DeleteMapping("/{id}/delete")
    public ResponseEntity<Boolean> deleteTurn(@PathVariable(name = "id") int id) {
        return ResponseEntity.ok(turnService.deleteTurnById(id));
    }
}
