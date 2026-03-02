package org.generation.italy.cinema.controllers;

import org.generation.italy.cinema.dto.BookedServiceDTO;
import org.generation.italy.cinema.model.entities.BookedService;
import org.generation.italy.cinema.model.entities.BookedServiceId;
import org.generation.italy.cinema.model.entities.Booking;
import org.generation.italy.cinema.model.entities.CinemaService;
import org.generation.italy.cinema.model.services.abstractions.iBookedService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/booked-services")
public class BookedServiceController {

    private final iBookedService service;

    public BookedServiceController(iBookedService service) {
        this.service = service;
    }

    //  GET ALL

    @GetMapping
    public List<BookedServiceDTO> findAll() {
        return service.findAll()
                .stream()
                .map(this::toDTO)
                .collect(Collectors.toList());
    }

    //  GET BY ID (CHIAVE COMPOSTA)
    // GET http://localhost:8080/api/booked-services/1/2 --> Se quella combinazione non esiste → 404 corretto.
    @GetMapping("/{bookingId}/{extraProductId}")
    public ResponseEntity<BookedServiceDTO> findById(
            @PathVariable Integer bookingId,
            @PathVariable Integer extraProductId) {

        BookedServiceId id = new BookedServiceId(bookingId, extraProductId);

        return service.findById(id)
                .map(entity -> ResponseEntity.ok(toDTO(entity)))
                .orElse(ResponseEntity.notFound().build());
    }

    //  GET BY BOOKING

    @GetMapping("/booking/{bookingId}")
    public List<BookedServiceDTO> findByBooking(@PathVariable Integer bookingId) {
        return service.findByBooking(bookingId)
                .stream()
                .map(this::toDTO)
                .collect(Collectors.toList());
    }

    //  GET BY EXTRA PRODUCT

    @GetMapping("/extra-product/{extraProductId}")
    public List<BookedServiceDTO> findByService(@PathVariable Integer extraProductId) {
        return service.findByService(extraProductId)
                .stream()
                .map(this::toDTO)
                .collect(Collectors.toList());
    }

    //  CREATE

    @PostMapping
    public ResponseEntity<BookedServiceDTO> create(@RequestBody BookedServiceDTO dto) {

        BookedService saved = service.create(toEntity(dto));

        return ResponseEntity.ok(toDTO(saved));
    }

    // DELETE

    @DeleteMapping("/{bookingId}/{extraProductId}")
    public ResponseEntity<Void> delete(
            @PathVariable Integer bookingId,
            @PathVariable Integer extraProductId) {

        BookedServiceId id = new BookedServiceId(bookingId, extraProductId);

        if (service.delete(id)) {
            return ResponseEntity.noContent().build();
        }

        return ResponseEntity.notFound().build();
    }

    //  MAPPER


    // Entity → DTO
    private BookedServiceDTO toDTO(BookedService entity) {

        BookedServiceDTO dto = new BookedServiceDTO();

        dto.setBookingId(entity.getBooking().getId());
        dto.setExtraProductId(entity.getService().getId());
        dto.setQuantity(entity.getQuantity());

        return dto;
    }

    // DTO → Entity
    private BookedService toEntity(BookedServiceDTO dto) {

        BookedService entity = new BookedService();

        Booking booking = new Booking();
        booking.setId(dto.getBookingId());

        CinemaService cinemaService = new CinemaService();
        cinemaService.setId(dto.getExtraProductId());

        entity.setBooking(booking);
        entity.setService(cinemaService);
        entity.setQuantity(dto.getQuantity());

        return entity;
    }
}
