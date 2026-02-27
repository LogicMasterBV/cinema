package org.generation.italy.cinema.model.services.abstractions;

import org.generation.italy.cinema.model.entities.Actor;
import org.generation.italy.cinema.model.entities.Director;
import org.generation.italy.cinema.model.entities.Film;
import org.generation.italy.cinema.model.entities.Genre;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

public interface FilmService {
    // restituisce tutti i film presenti
    List<Film> findFilmAll();
    //ricerca tramite id
    Optional<Film> findFilmById(int id);
    //cancellare un film
    boolean deleteFilmById(int id);
    // creazione film
    Film createFilm(Film film);
    // ricerca per titolo ma con contains
    List<Film> findFilmByTitle(String title);
    // modifica film
    boolean updateFilmById(int id);
    // ricerca per genere --repo
    List<Film> findFilmByGenre(Genre genre);
    //ricerca per attore --repo
    List<Film> findFilmByActor(Actor actor);
    //ricerca per regista --repo
    List<Film> findFilmByDirector(Director director);
    //film che sono disponibili per data -> proiezione --repo
    List<Film> findFilmByDateScreening(LocalDate date);
}
