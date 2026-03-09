package org.generation.italy.cinema.controllers;

import org.generation.italy.cinema.dto.FilmDTO;
import org.generation.italy.cinema.model.entities.Film;
import org.generation.italy.cinema.model.services.abstractions.iFilmService;
import org.springframework.core.io.Resource;
import org.springframework.core.io.UrlResource;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URI;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.time.LocalDate;
import java.util.List;
import java.util.UUID;

import static org.generation.italy.cinema.dto.FilmDTO.fromEntity;

@RestController
@RequestMapping("/api/film")
public class FilmController {
    private iFilmService service;
    private final Path rootLocation = Paths.get("upload-images");

    public FilmController(iFilmService service) {
        this.service = service;
        try {
            Files.createDirectories(rootLocation);
        } catch (IOException e) {
            throw new RuntimeException("Could not initialize storage", e);
        }
    }

    @GetMapping
    public List<FilmDTO> findAll(){
        List<Film> films = service.findFilmAll();
        return films.stream().map(FilmDTO :: fromEntity).toList();
    }

    @GetMapping("/{id}")
    public ResponseEntity<?> findById(@PathVariable int id) {
        return service.findFilmById(id).map(film -> {
            FilmDTO dto = FilmDTO.fromEntity(film);
            if (film.getImageUrl() != null && !film.getImageUrl().isEmpty()) {
                String fileUrl = ServletUriComponentsBuilder.fromCurrentContextPath()
                        .path("/api/film/images/")
                        .path(film.getImageUrl())
                        .toUriString();
                dto.setImageUrl(fileUrl);
            }
            return dto;
        }).map(ResponseEntity::ok).orElse(ResponseEntity.notFound().build());
    }

    @PostMapping(consumes = {MediaType.MULTIPART_FORM_DATA_VALUE})
    public ResponseEntity<FilmDTO> create(@RequestPart("film") FilmDTO dto,
                                          @RequestPart(value = "image", required = false) MultipartFile image) {
        Film film = dto.toEntity();
        if (image != null && !image.isEmpty()) {
            try {
                String filename = UUID.randomUUID().toString() + "_" + image.getOriginalFilename();
                Files.copy(image.getInputStream(), this.rootLocation.resolve(filename));
                film.setImageUrl(filename);
            } catch (Exception e) {
                throw new RuntimeException("FAIL!");
            }
        }
        Film film1 = service.createFilm(film);
        FilmDTO filmDTO = fromEntity(film1);
        URI location = URI.create("/api/film/" + film1.getId());
        return ResponseEntity.created(location).body(filmDTO);
    }

    @GetMapping("/images/{filename:.+}")
    @ResponseBody
    public ResponseEntity<Resource> getFile(@PathVariable String filename) {
        try {
            Path file = rootLocation.resolve(filename);
            Resource resource = new UrlResource(file.toUri());
            if (resource.exists() || resource.isReadable()) {
                return ResponseEntity.ok().body(resource);
            } else {
                throw new RuntimeException("Could not read file: " + filename);
            }
        } catch (MalformedURLException e) {
            throw new RuntimeException("Could not read file: " + filename, e);
        }
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
