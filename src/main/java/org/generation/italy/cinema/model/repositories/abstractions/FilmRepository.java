package org.generation.italy.cinema.model.repositories.abstractions;

import org.generation.italy.cinema.model.entities.Film;
import org.springframework.data.jpa.repository.JpaRepository;

public interface FilmRepository extends JpaRepository<Film, Integer> {

}
