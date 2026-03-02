package org.generation.italy.cinema.model.services.implementations;

import org.generation.italy.cinema.model.entities.Actor;
import org.generation.italy.cinema.model.repositories.abstractions.ActorRepository;
import org.generation.italy.cinema.model.services.abstractions.iActorService;

import java.util.List;
import java.util.Optional;

public class ActorService implements iActorService {

    private ActorRepository repo;

    public ActorService(ActorRepository repo) {
        this.repo = repo;
    }

    @Override
    public List<Actor> findAllActors() {
        return repo.findAll();
    }

    @Override
    public Optional<Actor> findActorById(int id) {
        return repo.findById(id);
    }

    @Override
    public boolean deleteActorById(int id) {
        if(repo.findById(id).isEmpty()){
            return false;
        }
        repo.deleteById(id);
        return true;
    }

    @Override
    public Actor createActor(Actor actor) {
        return repo.save(actor);
    }

    @Override
    public boolean updateActorById(int id, Actor update) {
        Optional<Actor> actor = repo.findById(id);
        if (actor.isEmpty()) {
            return false;
        }

        Actor exActor = actor.get();
        exActor.setFirstName(update.getFirstName());
        exActor.setBirthDate(update.getBirthDate());
        exActor.setFilms(update.getFilms());
        exActor.setLastName(update.getLastName());
        exActor.setNationality(update.getNationality());

        repo.save(exActor);
        return true;
    }}

