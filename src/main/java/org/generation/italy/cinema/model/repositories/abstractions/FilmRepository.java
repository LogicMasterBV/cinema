package org.generation.italy.cinema.model.repositories.abstractions;

import org.generation.italy.cinema.model.entities.Film;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.time.LocalDate;
import java.util.List;
import java.util.Set;

public interface FilmRepository extends JpaRepository<Film, Integer> {

    @Query("""
            SELECT DISTINCT f
            FROM Film f 
            JOIN f.screenings s
            WHERE s.screeningDate = :date
            """)
    List<Film> findFilmByDateScreening(@Param("date")LocalDate date);
    @Query("""
       SELECT DISTINCT f
       FROM Film f
       LEFT JOIN f.genres g
       LEFT JOIN f.actors a
       LEFT JOIN f.director d
       WHERE LOWER(f.title) LIKE LOWER(CONCAT('%', :q, '%'))
          OR LOWER(g.name) LIKE LOWER(CONCAT('%', :q, '%'))
          OR LOWER(a.firstName) LIKE LOWER(CONCAT('%', :q, '%'))
          OR LOWER(a.lastName) LIKE LOWER(CONCAT('%', :q, '%'))
          OR LOWER(d.firstName) LIKE LOWER(CONCAT('%', :q, '%'))
          OR LOWER(d.lastName) LIKE LOWER(CONCAT('%', :q, '%'))
       """)
    Page<Film> globalSearch(@Param("q") String query, Pageable pageable);

    @Query("""
    SELECT DISTINCT f FROM Film f
    JOIN f.genres g
    WHERE g.name IN :genreNames
    AND f.releaseDate BETWEEN :from AND :to
    ORDER BY f.releaseDate ASC
""")
    List<Film> findUpcomingByGenres(
            @Param("genreNames") Set<String> genreNames,
            @Param("from") LocalDate from,
            @Param("to") LocalDate to
    );
}
