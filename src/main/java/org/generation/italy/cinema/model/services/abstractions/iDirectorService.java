package org.generation.italy.cinema.model.services.abstractions;

import org.generation.italy.cinema.model.entities.Director;

import java.util.List;
import java.util.Optional;

public interface iDirectorService {
    Optional<Director> findDirectorById(int id);
    List<Director> findAllDirector();
    boolean deleteDirectorById(int id);
    boolean updateDirector(Director director);
    Director createDirector(Director director);
}
