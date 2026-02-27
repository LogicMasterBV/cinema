package org.generation.italy.cinema.model.repositories.abstractions;

import org.generation.italy.cinema.model.entities.Seat;
import org.springframework.data.jpa.repository.JpaRepository;

public interface SeatRepository extends JpaRepository<Seat, Integer> {
}
