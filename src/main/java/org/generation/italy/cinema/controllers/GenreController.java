package org.generation.italy.cinema.controllers;

import org.generation.italy.cinema.dto.GenreDTO;
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
    public ResponseEntity<List<GenreDTO>> getAll() {
        List<GenreDTO> genres = genreService.findAll()
                .stream()
                .map(GenreDTO::fromEntity)
                .toList();

        return ResponseEntity.ok(genres);
    }

    @GetMapping("/{id}")
    public ResponseEntity<GenreDTO> getById(@PathVariable Integer id) {
        Genre genre = genreService.findById(id);
        return ResponseEntity.ok(GenreDTO.fromEntity(genre));
    }

    @PostMapping
    public ResponseEntity<GenreDTO> create(@RequestBody Genre genre) {
        Genre savedGenre = genreService.save(genre);
        return ResponseEntity.ok(GenreDTO.fromEntity(savedGenre));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable Integer id) {
        genreService.delete(id);
        return ResponseEntity.noContent().build();
    }
}

