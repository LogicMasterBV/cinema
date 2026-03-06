package org.generation.italy.cinema.model.services.abstractions;

import org.generation.italy.cinema.model.entities.Hall;

import java.util.List;
import java.util.Optional;

public interface iHallService {
    List<Hall> findHallAll();
    Optional<Hall> findHallById(Integer id);
    boolean deleteHallById(Integer id);
    Hall createHall(Hall hall);
}
