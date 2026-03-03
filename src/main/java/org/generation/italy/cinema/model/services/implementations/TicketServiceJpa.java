package org.generation.italy.cinema.model.services.implementations;

import org.generation.italy.cinema.model.entities.Booking;
import org.generation.italy.cinema.model.entities.Screening;
import org.generation.italy.cinema.model.entities.Seat;
import org.generation.italy.cinema.model.entities.Ticket;
import org.generation.italy.cinema.model.repositories.abstractions.BookingRepository;
import org.generation.italy.cinema.model.repositories.abstractions.ScreeningRepository;
import org.generation.italy.cinema.model.repositories.abstractions.SeatRepository;
import org.generation.italy.cinema.model.repositories.abstractions.TicketRepository;
import org.generation.italy.cinema.model.services.abstractions.iTicketService;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.Optional;

@Service
public class TicketServiceJpa implements iTicketService {

    private final TicketRepository ticketRepository;
    private final BookingRepository bookingRepository;
    private final ScreeningRepository screeningRepository;
    private final SeatRepository seatRepository;

    public TicketServiceJpa(TicketRepository ticketRepository,
                            BookingRepository bookingRepository,
                            ScreeningRepository screeningRepository,
                            SeatRepository seatRepository) {
        this.ticketRepository = ticketRepository;
        this.bookingRepository = bookingRepository;
        this.screeningRepository = screeningRepository;
        this.seatRepository = seatRepository;
    }

    // READ

    @Override
    public Optional<Ticket> findTicketById(Integer id) {
        return ticketRepository.findById(id);
    }

    @Override
    public List<Ticket> findTicketsByBooking(Integer bookingId) {
        return ticketRepository.findByBookingId(bookingId);
    }

    @Override
    public Page<Ticket> findTicketsByScreening(Integer screeningId, Pageable pageable) {
        return ticketRepository.findByScreeningId(screeningId, pageable);
    }

    @Override
    public Page<Ticket> findTicketsByUser(Integer userId, Pageable pageable) {
        return ticketRepository.findByUserId(userId, pageable);
    }

    // SEATS / OCCUPANCY

    @Override
    public List<Integer> findOccupiedSeatIds(Integer screeningId) {
        return ticketRepository.findOccupiedSeatIds(screeningId);
    }

    @Override
    public boolean isSeatOccupied(Integer screeningId, Integer seatId) {
        return ticketRepository.existsByScreeningIdAndSeatId(screeningId, seatId);
    }


    // CREATE

    @Transactional
    @Override
    public Ticket createTicket(Integer bookingId,
                               Integer screeningId,
                               Integer seatId,
                               BigDecimal price) {

        // 1) carico oggetti collegati (FK)
        Booking booking = bookingRepository.findById(bookingId)
                .orElseThrow(() -> new NoSuchElementException("Booking not found id=" + bookingId));

        Screening screening = screeningRepository.findById(screeningId)
                .orElseThrow(() -> new NoSuchElementException("Screening not found id=" + screeningId));

        Seat seat = seatRepository.findById(seatId)
                .orElseThrow(() -> new NoSuchElementException("Seat not found id=" + seatId));

        // 2) coerenza: booking deve riferirsi a quello screening
        if (!booking.getScreening().getId().equals(screeningId)) {
            throw new IllegalArgumentException("Booking " + bookingId + " is not for screening " + screeningId);
        }

        // 3) coerenza: il seat deve appartenere alla hall della proiezione
        if (!seat.getHall().getId().equals(screening.getHall().getId())) {
            throw new IllegalArgumentException("Seat " + seatId + " does not belong to hall of screening " + screeningId);
        }

        // 4) regola business: no doppia vendita posto
        if (ticketRepository.existsByScreeningIdAndSeatId(screeningId, seatId)) {
            throw new IllegalStateException("Seat already occupied for screening. screeningId=" + screeningId + ", seatId=" + seatId);
        }

        // 5) creo ticket
        Ticket t = new Ticket();
        t.setBooking(booking);
        t.setScreening(screening);
        t.setSeat(seat);

        // prezzo: se null o <= 0, puoi anche defaultare a screening.basePrice
        if (price == null) {
            t.setPrice(screening.getBasePrice());
        } else {
            if (price.compareTo(BigDecimal.ZERO) < 0) {
                throw new IllegalArgumentException("Price must be >= 0");
            }
            t.setPrice(price);
        }

        // save = INSERT
        return ticketRepository.save(t);
    }

    // DELETE

    @Override
    public boolean deleteTicketById(Integer id) {
        if (!ticketRepository.existsById(id)) return false;
        ticketRepository.deleteById(id);
        return true;
    }
}