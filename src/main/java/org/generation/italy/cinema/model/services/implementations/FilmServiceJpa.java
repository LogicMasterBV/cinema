package org.generation.italy.cinema.model.services.implementations;

import org.generation.italy.cinema.model.entities.Film;
import org.generation.italy.cinema.model.repositories.abstractions.FilmRepository;
import org.generation.italy.cinema.model.services.abstractions.iFilmService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

@Service
public class FilmServiceJpa implements iFilmService {

    private FilmRepository repository;
    @Autowired
    public FilmServiceJpa(FilmRepository repository) {
        this.repository = repository;
    }

    @Override
    public List<Film> findFilmAll() {
        return repository.findAll();
    }

    @Override
    public Optional<Film> findFilmById(int id) {
        return repository.findById(id);
    }

    @Override
    public boolean deleteFilmById(int id) {
        Optional<Film> of = repository.findById(id);
        if(of.isPresent()){
            repository.deleteById(id);
            return true;
        }
        return false;
    }

    @Override
    public Film createFilm(Film film) {
        return repository.save(film);
    }

    @Override
    public boolean updateFilmById(Film film) {
        boolean update = repository.existsById(film.getId());
        if(film.getId() == null || !update){
            return false;
        }
        repository.save(film);
        return true;
    }

    @Override
    public List<Film> findFilmByDateScreening(LocalDate date) {
        return repository.findFilmByDateScreening(date);
    }

    @Override
    public List<Film> globalSearch(String query) {
        return repository.globalSearch(query);
    }
}
