package org.generation.italy.cinema.model.services.implementations;

import org.generation.italy.cinema.model.entities.Film;
import org.generation.italy.cinema.model.repositories.abstractions.FilmRepository;
import org.generation.italy.cinema.model.services.abstractions.iFilmService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

@Service
public class FilmService implements iFilmService {

    private FilmRepository repository;
    @Autowired
    public FilmService(FilmRepository repository) {
        this.repository = repository;
    }

    @Override
    public List<Film> findFilmAll() {
        return List.of();
    }

    @Override
    public Optional<Film> findFilmById(int id) {
        return Optional.empty();
    }

    @Override
    public boolean deleteFilmById(int id) {
        return false;
    }

    @Override
    public Film createFilm(Film film) {
        return null;
    }

    @Override
    public boolean updateFilmById(int id) {
        return false;
    }

    @Override
    public List<Film> findFilmByDateScreening(LocalDate date) {
        return List.of();
    }

    @Override
    public Page<Film> globalSearch(String query, Pageable pageable) {
        return repository.globalSearch(query, pageable);
    }
}
