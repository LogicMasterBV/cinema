package org.generation.italy.cinema.model.services.implementations;

import org.generation.italy.cinema.model.entities.Genre;
import org.generation.italy.cinema.model.repositories.abstractions.GenreRepository;
import org.generation.italy.cinema.model.services.abstractions.iGenreService;
import org.springframework.stereotype.Service;

import java.util.List;
@Service
public class GenreService implements iGenreService {
    private final GenreRepository genreRepository;
    public GenreService(GenreRepository genreRepository) {
        this.genreRepository = genreRepository;
    }
    @Override
    public List<Genre> findAll() {
        return genreRepository.findAll();
    }

    @Override
    public Genre findById(Integer id) {
        return genreRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Genre not found with id: " + id));
    }

    @Override
    public Genre save(Genre genre) {
        return genreRepository.save(genre);
    }

    @Override
    public void delete(Integer id) {
        if (!genreRepository.existsById(id)) {
            throw new RuntimeException("Cannot delete: Genre not found with id: " + id);
        }
        genreRepository.deleteById(id);
    }
}
