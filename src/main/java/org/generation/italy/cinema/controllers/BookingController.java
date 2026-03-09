package org.generation.italy.cinema.controllers;

import org.generation.italy.cinema.dto.BookingDTO;
import org.generation.italy.cinema.dto.BookingDetailsDTO;
import org.generation.italy.cinema.model.entities.Booking;
import org.generation.italy.cinema.model.entities.Screening;
import org.generation.italy.cinema.model.entities.User;
import org.generation.italy.cinema.model.services.abstractions.iBookingService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/bookings")
public class BookingController {

    private final iBookingService bookingService;

    public BookingController(iBookingService bookingService) {
        this.bookingService = bookingService;
    }
// Per testare scrivi: http://localhost:8080/api/bookings/user/1
    //  GET ALL
    @GetMapping
    public ResponseEntity<List<BookingDTO>> findAll() {

        List<BookingDTO> dtos = bookingService.findAll()
                .stream()
                .map(this::toDTO)
                .collect(Collectors.toList());

        return ResponseEntity.ok(dtos);
    }

    //  GET BY ID
    @GetMapping("/{id}")
    public ResponseEntity<BookingDTO> findById(@PathVariable Integer id) {

        return bookingService.findById(id)
                .map(b -> ResponseEntity.ok(toDTO(b)))
                .orElse(ResponseEntity.notFound().build());
    }

    //  GET BY USER
    @GetMapping("/user/{userId}")
    public ResponseEntity<List<BookingDTO>> findByUser(@PathVariable Integer userId) {

        List<BookingDTO> dtos = bookingService.findByUser(userId)
                .stream()
                .map(this::toDTO)
                .collect(Collectors.toList());

        return ResponseEntity.ok(dtos);
    }

    //  GET BY SCREENING
    @GetMapping("/screening/{screeningId}")
    public ResponseEntity<List<BookingDTO>> findByScreening(@PathVariable Integer screeningId) {

        List<BookingDTO> dtos = bookingService.findByScreening(screeningId)
                .stream()
                .map(this::toDTO)
                .collect(Collectors.toList());

        return ResponseEntity.ok(dtos);
    }

    //  CREATE
    @PostMapping
    public ResponseEntity<BookingDTO> create(@RequestBody BookingDTO dto) {

        Booking booking = toEntity(dto);
        Booking saved = bookingService.create(booking);

        return ResponseEntity.ok(toDTO(saved));
    }

    //  DELETE
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable Integer id) {

        boolean deleted = bookingService.deleteById(id);

        if (!deleted) {
            return ResponseEntity.notFound().build();
        }

        return ResponseEntity.noContent().build();
    }

    //  MAPPER

    private BookingDTO toDTO(Booking booking) {
        return new BookingDTO(
                booking.getId(),
                booking.getUser().getId(),
                booking.getScreening().getId(),
                booking.getTotalPrice(),
                booking.getBookingDate()
        );
    }
    @GetMapping("/user/{userId}/details")
    public ResponseEntity<List<BookingDetailsDTO>> getBookingDetails(
            @PathVariable Integer userId) {

        return ResponseEntity.ok(
                bookingService.findBookingDetailsByUser(userId)
        );
    }

    private Booking toEntity(BookingDTO dto) {

        Booking booking = new Booking();
        booking.setId(dto.getId());
        booking.setTotalPrice(dto.getTotalPrice());

        User user = new User();
        user.setId(dto.getUserId());
        booking.setUser(user);

        Screening screening = new Screening();
        screening.setId(dto.getScreeningId());
        booking.setScreening(screening);

        return booking;
    }
}
