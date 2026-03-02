package org.generation.italy.cinema.controllers;

import org.generation.italy.cinema.model.entities.Ticket;
import org.generation.italy.cinema.model.services.abstractions.iTicketService;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.math.BigDecimal;
import java.net.URI;
import java.util.List;

@RestController
@RequestMapping("/api/tickets")
public class TicketController {

    private final iTicketService ticketService;

    public TicketController(iTicketService ticketService) {
        this.ticketService = ticketService;
    }

    // -------------------------------------------------
    // GET ticket by id
    // -------------------------------------------------
    @GetMapping("/{id}")
    public ResponseEntity<Ticket> getById(@PathVariable Integer id) {
        return ticketService.findTicketById(id)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    // -------------------------------------------------
    // GET tickets by booking
    // -------------------------------------------------
    @GetMapping("/by-booking/{bookingId}")
    public ResponseEntity<List<Ticket>> getByBooking(@PathVariable Integer bookingId) {
        return ResponseEntity.ok(ticketService.findTicketsByBooking(bookingId));
    }

    // -------------------------------------------------
    // GET tickets by screening (paginato)
    // -------------------------------------------------
    @GetMapping("/by-screening/{screeningId}")
    public ResponseEntity<Page<Ticket>> getByScreening(@PathVariable Integer screeningId,
                                                       Pageable pageable) {
        return ResponseEntity.ok(
                ticketService.findTicketsByScreening(screeningId, pageable)
        );
    }

    // -------------------------------------------------
    // GET tickets by user (paginato)
    // -------------------------------------------------
    @GetMapping("/by-user/{userId}")
    public ResponseEntity<Page<Ticket>> getByUser(@PathVariable Integer userId,
                                                  Pageable pageable) {
        return ResponseEntity.ok(
                ticketService.findTicketsByUser(userId, pageable)
        );
    }

    // -------------------------------------------------
    // GET occupied seats for screening
    // -------------------------------------------------
    @GetMapping("/occupied-seats/{screeningId}")
    public ResponseEntity<List<Integer>> occupiedSeats(@PathVariable Integer screeningId) {
        return ResponseEntity.ok(
                ticketService.findOccupiedSeatIds(screeningId)
        );
    }

    // -------------------------------------------------
    // POST create ticket
    // -------------------------------------------------
    @PostMapping
    public ResponseEntity<Ticket> create(@RequestParam Integer bookingId,
                                         @RequestParam Integer screeningId,
                                         @RequestParam Integer seatId,
                                         @RequestParam(required = false) BigDecimal price) {

        Ticket created = ticketService.createTicket(
                bookingId,
                screeningId,
                seatId,
                price
        );

        return ResponseEntity
                .created(URI.create("/api/tickets/" + created.getId()))
                .body(created);
    }

    // -------------------------------------------------
    // DELETE ticket
    // -------------------------------------------------
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable Integer id) {

        boolean deleted = ticketService.deleteTicketById(id);

        return deleted
                ? ResponseEntity.noContent().build()
                : ResponseEntity.notFound().build();
    }
}