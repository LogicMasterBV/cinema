package org.generation.italy.cinema.controllers;

import org.generation.italy.cinema.dto.HallDTO;
import org.generation.italy.cinema.model.entities.Hall;
import org.generation.italy.cinema.model.services.abstractions.iHallService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/halls")
public class HallController {
    private final iHallService service;

    public HallController(iHallService service) {
        this.service = service;
    }

    @GetMapping
    public ResponseEntity<List<HallDTO>> findHallAll() {
        List<HallDTO> list = service.findHallAll()
                .stream()
                .map(HallDTO::fromEntity)
                .toList();
        return ResponseEntity.ok(list);
    }

    @GetMapping("/{id}")
    public ResponseEntity<HallDTO> findHallById(@PathVariable Integer id) {
       return service.findHallById(id)
               .map(HallDTO::fromEntity)
               .map(ResponseEntity::ok)
               .orElse(ResponseEntity.notFound().build());
    }

    @PostMapping
    public ResponseEntity<HallDTO> createHall(@RequestBody HallDTO dto) {
        Hall saved = service.createHall(HallDTO.toEntity(dto));
        return ResponseEntity.ok(HallDTO.fromEntity(saved));
    }
}
