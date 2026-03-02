package org.generation.italy.cinema.model.services.abstractions;

import org.generation.italy.cinema.model.entities.Ticket;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.math.BigDecimal;
import java.util.List;
import java.util.Optional;

public interface iTicketService {

    // crud base
    Optional<Ticket> findTicketById(Integer id);
    boolean deleteTicketById(Integer id);

    // Liste
    List<Ticket> findTicketsByBooking(Integer bookingId);
    Page<Ticket> findTicketsByScreening(Integer screeningId, Pageable pageable);
    Page<Ticket> findTicketsByUser(Integer userId, Pageable pageable);

    // posti occupati
    List<Integer> findOccupiedSeatIds(Integer screeningId);
    boolean isSeatOccupied(Integer screeningId, Integer seatId);

    // creazione ticket
    Ticket createTicket(Integer bookingId, Integer screeningId, Integer seatId, BigDecimal price);
}