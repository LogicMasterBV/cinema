package org.generation.italy.cinema.controllers;

import org.generation.italy.cinema.dto.ScreeningDTO;
import org.generation.italy.cinema.model.entities.Film;
import org.generation.italy.cinema.model.entities.Hall;
import org.generation.italy.cinema.model.entities.Screening;
import org.generation.italy.cinema.model.entities.Seat;
import org.generation.italy.cinema.model.services.abstractions.iScreeningService;
import org.generation.italy.cinema.model.services.implementations.ScreeningServiceJpa;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.util.List;

@RestController
@RequestMapping("/api/screenings")
public class ScreeningController {

    private final iScreeningService screeningService;

    // @Autowired non necessario se si ha un solo costruttore
    public ScreeningController(ScreeningServiceJpa screeningService) {
        this.screeningService = screeningService;
    }

    @GetMapping
    public ResponseEntity<List<ScreeningDTO>> getAll() {
        //List<Screening> screenings = screeningService.getAll();
        //List<ScreeningDTO> result = new ArrayList<>();
        //for (Screening screening : screenings) {
        //    result.add(new ScreeningDTO(screening)); // usa il costruttore del DTO
        //}
        // È più pulito in questo modo :
        List<ScreeningDTO> result = screeningService.getAll()    // restituisce List<Screening>
                .stream()                // trasforma la lista in uno stream
                .map(ScreeningDTO::new)  // converte ogni Screening in ScreeningDTO usando il costruttore
                .toList();               // raccoglie il risultato in una nuova List<ScreeningDTO>
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
        Screening screening = new Screening();  // crea una nuova entità vuota

        Film film = new Film();
        film.setId(dto.getFilmId());            // prende l'id del film dal DTO
        // e crea un oggetto Film con solo l'id
        Hall hall = new Hall();
        hall.setId(dto.getHallId());            // stesso cosa per la sala

        screening.setFilm(film);               // assegna il film alla proiezione
        screening.setHall(hall);               // assegna la sala alla proiezione
        screening.setScreeningDate(dto.getScreeningDate());  // copia la data
        screening.setScreeningTime(dto.getScreeningTime());  // copia l'orario

        return screening;                       // restituisce l'entità pronta
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
