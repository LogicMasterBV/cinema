package org.generation.italy.cinema.controllers;

import org.generation.italy.cinema.dto.ScreeningDTO;
import org.generation.italy.cinema.model.entities.Film;
import org.generation.italy.cinema.model.entities.Hall;
import org.generation.italy.cinema.model.entities.Screening;
import org.generation.italy.cinema.model.entities.Seat;
import org.generation.italy.cinema.model.services.abstractions.iScreeningService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.util.List;

@RestController
@RequestMapping("/api/screenings")
public class ScreeningController {

    private final iScreeningService screeningService;

    public ScreeningController(iScreeningService screeningService) {
        this.screeningService = screeningService;
    }

    @GetMapping
    public ResponseEntity<List<ScreeningDTO>> getAll() {
        List<ScreeningDTO> result = screeningService.getAll()
                .stream()
                .map(ScreeningDTO::new)
                .toList();
        return ResponseEntity.ok(result);
    }

    @GetMapping("/{id}")
    public ResponseEntity<ScreeningDTO> getById(@PathVariable Integer id) {
        return ResponseEntity.ok(new ScreeningDTO(screeningService.getById(id)));
    }

    @GetMapping("/film/{filmId}")
    public ResponseEntity<List<ScreeningDTO>> getByFilm(@PathVariable Integer filmId) {
        List<ScreeningDTO> result = screeningService.getByFilm(filmId)
                .stream()
                .map(ScreeningDTO::new)
                .toList();
        return ResponseEntity.ok(result);
    }

    @GetMapping("/date")
    public ResponseEntity<List<ScreeningDTO>> getByDate(@RequestParam LocalDate date) {
        List<ScreeningDTO> result = screeningService.getByDate(date)
                .stream()
                .map(ScreeningDTO::new)
                .toList();
        return ResponseEntity.ok(result);
    }

    @GetMapping("/date/range")
    public ResponseEntity<List<ScreeningDTO>> getByDateBetween(@RequestParam LocalDate from,
                                                               @RequestParam LocalDate to) {
        List<ScreeningDTO> result = screeningService.getByDateBetween(from, to)
                .stream()
                .map(ScreeningDTO::new)
                .toList();
        return ResponseEntity.ok(result);
    }

    @GetMapping("/hall/{hallId}")
    public ResponseEntity<List<ScreeningDTO>> getByHall(@PathVariable Integer hallId) {
        List<ScreeningDTO> result = screeningService.getByHall(hallId)
                .stream()
                .map(ScreeningDTO::new)
                .toList();
        return ResponseEntity.ok(result);
    }

    @GetMapping("/hall/{hallId}/date")
    public ResponseEntity<List<ScreeningDTO>> getByHallAndDate(@PathVariable Integer hallId,
                                                               @RequestParam LocalDate date) {
        List<ScreeningDTO> result = screeningService.getByHallAndDate(hallId, date)
                .stream()
                .map(ScreeningDTO::new)
                .toList();
        return ResponseEntity.ok(result);
    }

    @GetMapping("/{id}/seats/available")
    public ResponseEntity<List<Seat>> getAvailableSeats(@PathVariable Integer id) {
        return ResponseEntity.ok(screeningService.getAvailableSeats(id));
    }

    private Screening toEntity(ScreeningDTO dto) {
        Screening screening = new Screening();

        Film film = new Film();
        film.setId(dto.getFilmId());

        Hall hall = new Hall();
        hall.setId(dto.getHallId());

        screening.setFilm(film);
        screening.setHall(hall);
        screening.setScreeningDate(dto.getScreeningDate());
        screening.setScreeningTime(dto.getScreeningTime());
        screening.setBasePrice(dto.getBasePrice());

        return screening;
    }

    @PostMapping
    public ResponseEntity<ScreeningDTO> create(@RequestBody ScreeningDTO dto) {
        Screening screening = toEntity(dto);
        return ResponseEntity.status(HttpStatus.CREATED)
                .body(new ScreeningDTO(screeningService.create(screening)));
    }

    @PutMapping("/{id}")
    public ResponseEntity<ScreeningDTO> update(@PathVariable Integer id,
                                               @RequestBody ScreeningDTO dto) {
        Screening screening = toEntity(dto);
        return ResponseEntity.ok(new ScreeningDTO(screeningService.update(id, screening)));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable Integer id) {
        screeningService.delete(id);
        return ResponseEntity.noContent().build();
    }
}
