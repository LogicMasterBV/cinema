package org.generation.italy.cinema.controllers;

import org.generation.italy.cinema.model.entities.Seat;
import org.generation.italy.cinema.model.services.abstractions.iSeatService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/seats")
public class SeatController {

    private final iSeatService seatService;

    public SeatController(iSeatService seatService) {
        this.seatService = seatService;
    }

    // Ritorna tutti i posti
    @GetMapping
    public List<Seat> getAll() {
        return seatService.getAll();
    }

    // Ritorna un posto per id
    @GetMapping("/{id}")
    public Seat getById(@PathVariable int id) {
        return seatService.getById(id);
    }

    // Ritorna tutti i posti di una sala (ordinati come da query repository)
    @GetMapping("/hall/{hallId}")
    public List<Seat> getByHall(@PathVariable int hallId) {
        return seatService.getByHallId(hallId);
    }

    // Crea un nuovo posto
    @PostMapping
    public Seat create(@RequestBody Seat seat) {
        return seatService.create(seat);
    }

    // Aggiorna un posto esistente
    @PutMapping("/{id}")
    public Seat update(@PathVariable int id, @RequestBody Seat seat) {
        return seatService.update(id, seat);
    }

    // Elimina un posto
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable int id) {
        boolean deleted = seatService.delete(id);
        return deleted ? ResponseEntity.noContent().build()
                : ResponseEntity.notFound().build();
    }
}