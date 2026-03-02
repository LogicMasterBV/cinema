package org.generation.italy.cinema.model.services.abstractions;

import org.generation.italy.cinema.model.entities.Genre;

import java.util.List;

public interface iGenreService {
    List<Genre> findAll();
    Genre findById(Integer id);
    Genre save(Genre genre);
    void delete(Integer id);
}
