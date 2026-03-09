package org.generation.italy.cinema.controllers;

import org.generation.italy.cinema.dto.GenreDTO;
import org.generation.italy.cinema.model.entities.Genre;
import org.generation.italy.cinema.model.services.abstractions.iGenreService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/genres")
public class GenreController {
    private final iGenreService genreService;
    public GenreController(iGenreService genreService) {
        this.genreService = genreService;
    }

    @GetMapping
    public ResponseEntity<List<GenreDTO>> getAll() {
        List<Genre> genres = genreService.findAll();
        List<GenreDTO> dtos = genres.stream()
                .map(GenreDTO::fromEntity)
                .collect(Collectors.toList());
        return ResponseEntity.ok(dtos);
    }

    @GetMapping("/{id}")
    public ResponseEntity<GenreDTO> getById(@PathVariable Integer id) {
        Genre genre = genreService.findById(id);
        return ResponseEntity.ok(GenreDTO.fromEntity(genre));
    }

    @PostMapping
    public ResponseEntity<GenreDTO> create(@RequestBody GenreDTO genreDTO) {
        Genre genre = new Genre();
        genre.setName(genreDTO.getName());
        Genre savedGenre = genreService.save(genre);
        return ResponseEntity.ok(GenreDTO.fromEntity(savedGenre));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable Integer id) {
        genreService.delete(id);
        return ResponseEntity.noContent().build();
    }
}
