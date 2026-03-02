package org.generation.italy.cinema.model.services.implementations;

import org.generation.italy.cinema.model.entities.Director;
import org.generation.italy.cinema.model.repositories.abstractions.DirectorRepository;
import org.generation.italy.cinema.model.services.abstractions.iDirectorService;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
@Service
public class DirectorService implements iDirectorService {

    private DirectorRepository repository;

    public DirectorService(DirectorRepository repository) {
        this.repository = repository;
    }

    @Override
    public Optional<Director> findDirectorById(int id) {
        return repository.findById(id);
    }

    @Override
    public List<Director> findAllDirector() {
        return repository.findAll();
    }

    @Override
    public boolean deleteDirectorById(int id) {
        Optional<Director> od = repository.findById(id);
        if(od.isPresent()){
            repository.deleteById(id);
            return true;
        }
        return false;
    }
    // da modificare come quello di Tummi Michele
    @Override
    public boolean updateDirector(Director director) {
        if(!repository.existsById(director.getId())){
            return false;
        }
        repository.save(director);
        return true;
    }

    @Override
    public Director createDirector(Director director) {
        return repository.save(director);
    }
}
