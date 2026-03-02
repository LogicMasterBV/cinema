package org.generation.italy.cinema.model.repositories.abstractions;

import org.generation.italy.cinema.model.entities.CinemaService;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CinemaServiceRepository extends JpaRepository<CinemaService, Integer> {
}
