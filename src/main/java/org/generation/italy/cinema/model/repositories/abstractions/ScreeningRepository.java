package org.generation.italy.cinema.model.repositories.abstractions;

import org.generation.italy.cinema.model.entities.Screening;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ScreeningRepository extends JpaRepository<Screening, Integer> {
}
