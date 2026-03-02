package org.generation.italy.cinema.controllers;

import org.generation.italy.cinema.dto.DirectorDTO;
import org.generation.italy.cinema.model.entities.Director;
import org.generation.italy.cinema.model.services.abstractions.iDirectorService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.net.URI;
import java.util.List;
import java.util.Optional;

import static org.generation.italy.cinema.dto.DirectorDTO.fromDirector;

@RestController
@RequestMapping("/api/director")
public class DirectorController {
    private iDirectorService service;

    public DirectorController(iDirectorService service) {
        this.service = service;
    }

    @GetMapping("/{id}")
    public ResponseEntity<?> findById(@PathVariable int id){
        Optional<Director> od = service.findDirectorById(id);
        if(od.isPresent()){
            return ResponseEntity.ok(fromDirector(od.get()));
        }
        return ResponseEntity.notFound().build();
    }

    @GetMapping
    public List<DirectorDTO> findAll(){
        List<Director> directors = service.findAllDirector();
        return directors.stream().map(DirectorDTO::fromDirector).toList();
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteById(@PathVariable int id){
        boolean deleted = service.deleteDirectorById(id);
        if(deleted){
            return ResponseEntity.noContent().build();
        }
        return ResponseEntity.notFound().build();
    }

    @PutMapping("/{id}")
    public ResponseEntity<?> update(@PathVariable int id, @RequestBody DirectorDTO directorDTO){
        //controllo se l'id inserito è quello del director che voglio updatare
        if(id != directorDTO.getId()){
            return ResponseEntity.badRequest().body("L'id del path deve coincidere con quello della entity");
        }
        Director dir = directorDTO.toEntity();
        boolean update = service.updateDirector(dir);
        if(update){
            // entity -> dto
            return ResponseEntity.ok(fromDirector(dir));
        }
        return ResponseEntity.notFound().build();
    }

    @PostMapping
    public ResponseEntity<DirectorDTO> create(@RequestBody DirectorDTO directorDTO){
        Director dir = directorDTO.toEntity();
        Director dirCreated = service.createDirector(dir);
        DirectorDTO dto = fromDirector(dirCreated);
        URI location = URI.create("/api/director/" + dirCreated.getId());
        return ResponseEntity.created(location).body(dto);
    }
}
