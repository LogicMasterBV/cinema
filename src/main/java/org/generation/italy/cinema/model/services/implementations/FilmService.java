package org.generation.italy.cinema.model.services.implementations;

import org.generation.italy.cinema.model.repositories.abstractions.FilmRepository;
import org.springframework.stereotype.Service;

@Service
public class FilmService {

    private FilmRepository repository;

    public FilmService(FilmRepository repository) {
        this.repository = repository;
    }
}
