package org.generation.italy.cinema.model.services.abstractions;

import org.generation.italy.cinema.model.entities.Actor;
import org.generation.italy.cinema.model.entities.Film;

import java.util.List;
import java.util.Optional;

public interface iActorService {
    List<Actor> findAllActors();
    Optional<Actor> findActorById(int id);
    boolean deleteActorById(int id);
    Actor createActor(Actor actor);
    boolean updateActorById(int id, Actor update);
}
