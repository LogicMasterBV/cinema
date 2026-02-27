package org.generation.italy.cinema.model.repositories.abstractions;

import org.generation.italy.cinema.model.entities.Film;
import org.generation.italy.cinema.model.entities.Genre;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface FilmRepository extends JpaRepository<Film, Integer> {

    @Query(
        """
        SELECT f
        FROM Film f
        WHERE LOWER(f.title) LIKE LOWER(CONCAT('%' , :filmTitle, '%'))
        """
    )
    List<Film> findFilmByTitle(@Param("filmTitle") String title);

}
