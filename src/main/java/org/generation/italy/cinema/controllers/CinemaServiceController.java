package org.generation.italy.cinema.controllers;

import org.generation.italy.cinema.model.entities.CinemaService;
import org.generation.italy.cinema.model.services.abstractions.iCinemaService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/cinema-services")
public class CinemaServiceController {
    private final iCinemaService cinemaService;

    public CinemaServiceController(iCinemaService cinemaService) {
        this.cinemaService = cinemaService;
    }

    @GetMapping
    public ResponseEntity<List<CinemaService>> getAll() {
        return ResponseEntity.ok(cinemaService.findAll());
    }

    @GetMapping("/{id}")
    public ResponseEntity<Optional<CinemaService>> getById(@PathVariable Integer id) {
        return ResponseEntity.ok(cinemaService.findById(id));
    }

    @PostMapping
    public ResponseEntity<CinemaService> create(@RequestBody CinemaService service) {
        // Be careful: The user must send JSON fields matching the Entity
        return ResponseEntity.ok(cinemaService.save(service));
    }
}
