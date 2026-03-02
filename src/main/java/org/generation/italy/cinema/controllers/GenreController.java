package org.generation.italy.cinema.controllers;

import org.generation.italy.cinema.model.entities.Genre;
import org.generation.italy.cinema.model.services.abstractions.iGenreService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/genres")
public class GenreController {
    private final iGenreService genreService;
    public GenreController(iGenreService genreService) {
        this.genreService = genreService;
    }

    @GetMapping
    public ResponseEntity<List<Genre>> getAll() {
        return ResponseEntity.ok(genreService.findAll());
    }

    @GetMapping("/{id}")
    public ResponseEntity<Genre> getById(@PathVariable Integer id) {
        return ResponseEntity.ok(genreService.findById(id));
    }

    @PostMapping
    public ResponseEntity<Genre> create(@RequestBody Genre genre) {
        return ResponseEntity.ok(genreService.save(genre));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable Integer id) {
        genreService.delete(id);
        return ResponseEntity.noContent().build();
    }
}
