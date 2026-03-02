package org.generation.italy.cinema.model.services.abstractions;

import org.generation.italy.cinema.model.entities.CinemaService;

import java.util.List;
import java.util.Optional;

public interface iCinemaService {
    List<CinemaService> findAll();
    Optional<CinemaService> findById(Integer id);
    CinemaService save(CinemaService service);
    void delete(Integer id);
}
