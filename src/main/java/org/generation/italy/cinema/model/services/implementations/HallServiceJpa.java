package org.generation.italy.cinema.model.services.implementations;

import org.generation.italy.cinema.model.entities.Hall;
import org.generation.italy.cinema.model.repositories.abstractions.HallRepository;
import org.generation.italy.cinema.model.services.abstractions.iHallService;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
@Service
public class HallServiceJpa implements iHallService {
    HallRepository repo;

    public HallServiceJpa(HallRepository repo) {
        this.repo = repo;
    }

    @Override
    public List<Hall> findHallAll() {
        return repo.findAll();
    }

    @Override
    public Optional<Hall> findHallById(int id) {
        return repo.findById(id);
    }


    @Override
    public Hall createHall(Hall hall) {
        return repo.save(hall);
    }

    @Override
    public boolean deleteHallById(int id) {
        if(repo.findById(id).isEmpty()) {
            return false;
        }
        repo.deleteById(id);
        return true;


    }
}
