package org.generation.italy.cinema.controllers;

import org.generation.italy.cinema.dto.ActorDTO;
import org.generation.italy.cinema.model.entities.Actor;
import org.generation.italy.cinema.model.services.abstractions.iActorService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/actors")
public class ActorController {
    private final iActorService service;

    public ActorController(iActorService service) {
        this.service = service;
    }

    @GetMapping
    public ResponseEntity<List<ActorDTO>> getAllActors() {
        List<ActorDTO> list = service.findAllActors()
                .stream()
                .map(ActorDTO::fromEntity)
                .toList();

        return ResponseEntity.ok(list);
    }

    @GetMapping("/{id}")
    public ResponseEntity<ActorDTO> getActorById(@PathVariable Integer id) {
        return service.findActorById(id)
                .map(ActorDTO::fromEntity)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @PostMapping
    public ResponseEntity<ActorDTO> createActor(@RequestBody ActorDTO dto) {
        Actor saved = service.createActor(ActorDTO.toEntity(dto));
        return ResponseEntity.ok(ActorDTO.fromEntity(saved));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteActor (@PathVariable Integer id) {
        boolean deleted = service.deleteActorById(id);

        if(!deleted){
            return ResponseEntity.notFound().build();
        }

        return ResponseEntity.noContent().build();
    }

    @PutMapping("/{id}")
    public ResponseEntity<ActorDTO> updateActor(@PathVariable Integer id, @RequestBody ActorDTO dto){
        boolean updated = service.updateActorById(id, ActorDTO.toEntity(dto));

        if (!updated) {
            return ResponseEntity.notFound().build();
        }

        return ResponseEntity.noContent().build();
    }
}
