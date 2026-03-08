package org.generation.italy.cinema.controllers;

import org.generation.italy.cinema.dto.FilmDTO;
import org.generation.italy.cinema.model.entities.Film;
import org.generation.italy.cinema.model.services.abstractions.iFilmService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.net.URI;
import java.time.LocalDate;
import java.util.List;

import static org.generation.italy.cinema.dto.FilmDTO.fromEntity;

@RestController
@RequestMapping("/api/film")
public class FilmController {
    private iFilmService service;

    public FilmController(iFilmService service) {
        this.service = service;
    }

    @GetMapping
    public List<FilmDTO> findAll(){
        List<Film> films = service.findFilmAll();
        return films.stream().map(FilmDTO :: fromEntity).toList();
    }

    @GetMapping("/{id}")
    public ResponseEntity<?> findById(@PathVariable int id) {
        return service.findFilmById(id).map(FilmDTO :: fromEntity)
                .map(ResponseEntity :: ok).orElse(ResponseEntity.notFound().build());
    }

    @PostMapping
    public ResponseEntity<FilmDTO> create(@RequestBody FilmDTO dto) {
        Film film = dto.toEntity();
        Film film1 = service.createFilm(film);
        FilmDTO filmDTO = fromEntity(film1);
        URI location = URI.create("/api/film/" + film1.getId());
        return ResponseEntity.created(location).body(filmDTO);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteById(@PathVariable int id) {
        boolean delete = service.deleteFilmById(id);

        if (delete) {
            return ResponseEntity.noContent().build();
        }
        return ResponseEntity.notFound().build();
    }

    @GetMapping("/search")
    public ResponseEntity<List<FilmDTO>> search(@RequestParam String q)  {
        List<FilmDTO> result = service.globalSearch(q)
                .stream()
                .map(FilmDTO :: fromEntity)
                .toList();

        return ResponseEntity.ok(result);
    }

    @GetMapping("/by-date")
    public ResponseEntity<List<FilmDTO>> findByDate(
            @RequestParam LocalDate date) {

        List<FilmDTO> films = service.findFilmByDateScreening(date)
                .stream()
                .map(FilmDTO::fromEntity)
                .toList();

        return ResponseEntity.ok(films);
    }

    @PutMapping("/{id}")
    public ResponseEntity<Void> update(@PathVariable int id,
                                       @RequestBody FilmDTO dto) {

        Film film = dto.toEntity();
        film.setId(id);
        boolean updated = service.updateFilmById(film);
        if (updated) {
            return ResponseEntity.noContent().build();
        }
        return ResponseEntity.notFound().build();
    }
}
