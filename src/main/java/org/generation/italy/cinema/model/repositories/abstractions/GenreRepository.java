package org.generation.italy.cinema.model.repositories.abstractions;

import org.generation.italy.cinema.model.entities.Genre;
import org.springframework.data.jpa.repository.JpaRepository;

public interface GenreRepository extends JpaRepository<Genre, Integer> {
}
