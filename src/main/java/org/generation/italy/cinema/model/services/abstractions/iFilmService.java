package org.generation.italy.cinema.model.services.abstractions;

import org.generation.italy.cinema.model.entities.Film;
import org.springframework.data.domain.Page;

import org.springframework.data.domain.Pageable;
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
    boolean updateFilmById(int id);
    // ricerca per genere --repo
    List<Film> findFilmByDateScreening(LocalDate date);
    Page<Film> globalSearch(String query, Pageable pageable);
}
