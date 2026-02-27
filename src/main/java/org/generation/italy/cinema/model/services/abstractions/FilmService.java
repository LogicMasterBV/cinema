package org.generation.italy.cinema.model.services.abstractions;

import org.generation.italy.cinema.model.entities.Actor;
import org.generation.italy.cinema.model.entities.Director;
import org.generation.italy.cinema.model.entities.Film;
import org.generation.italy.cinema.model.entities.Genre;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

public interface FilmService {
    List<Film> findFilmAll();

    Optional<Film> findFilmById(int id);

    boolean deleteFilmById(int id);

    boolean updateFilmById(int id);

    Film createFilm(Film film);

    List<Film> findFilmByTitle(String title);

    List<Film> findFilmByGenre(String genreId);

    List<Film> findFilmByActor(String actorId);

    List<Film> findFilmByDirector(String director);

    List<Film> findFilmByDateScreening(LocalDate date);
}
