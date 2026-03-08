package org.generation.italy.cinema.model.services.abstractions;

import org.generation.italy.cinema.model.entities.Film;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

public interface iFilmService {
    // restituisce tutti i film presenti
    List<Film> findFilmAll();
    //ricerca tramite id
    Optional<Film> findFilmById(int id);
    //cancellare un film
    boolean deleteFilmById(int id);
    // creazione film
    Film createFilm(Film film);
    // modifica film
    boolean updateFilmById(Film film);
    // ricerca per genere --repo
    List<Film> findFilmByDateScreening(LocalDate date);
    List<Film> globalSearch(String query);
}
