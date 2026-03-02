package org.generation.italy.cinema.model.services.implementations;

import org.generation.italy.cinema.model.entities.CinemaService;
import org.generation.italy.cinema.model.repositories.abstractions.CinemaServiceRepository;
import org.generation.italy.cinema.model.services.abstractions.iCinemaService;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class CinemaServiceJpa implements iCinemaService {
    private CinemaServiceRepository cinemaService;

    public CinemaServiceJpa(CinemaServiceRepository cinemaService) {
        this.cinemaService = cinemaService;
    }

    @Override
    public List<org.generation.italy.cinema.model.entities.CinemaService> findAll() {
        // Returns the list of entities directly
        return cinemaService.findAll();
    }

    @Override
    public Optional<CinemaService> findById(Integer id) {
        return cinemaService.findById(id);
    }

    @Override
    public CinemaService save(CinemaService service) {
        // Saves the entity object received from the controller
        return cinemaService.save(service);
    }
    @Override
    public void delete(Integer id) {
        if (!cinemaService.existsById(id)) {
            throw new RuntimeException("Cannot delete: Service not found with id " + id);
        }
        cinemaService.deleteById(id);
    }
}
