package org.generation.italy.cinema.model.repositories.abstractions;

import org.generation.italy.cinema.model.entities.Ticket;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Optional;

public interface TicketRepository extends JpaRepository<Ticket, Integer> {

    // 1) Tutti i ticket di una prenotazione
    List<Ticket> findByBookingId(Integer bookingId);

    // 2) Ticket di una proiezione (paginati)
    Page<Ticket> findByScreeningId(Integer screeningId, Pageable pageable);

    // 3) Ticket di un utente (tramite booking -> user)
    @Query("""
        SELECT t
        FROM Ticket t
        JOIN t.booking b
        JOIN b.user u
        WHERE u.id = :userId
        ORDER BY t.date DESC
        """)
    Page<Ticket> findByUserId(@Param("userId") Integer userId, Pageable pageable);

    // 4) Verifica se un posto è già occupato (per evitare doppie prenotazioni)
    boolean existsByScreeningIdAndSeatId(Integer screeningId, Integer seatId);

    // 5) Recupera un ticket specifico di uno screening+seat (utile)
    Optional<Ticket> findByScreeningIdAndSeatId(Integer screeningId, Integer seatId);

    // 6) Lista ID posti occupati in uno screening (per UI “mappa posti”)
    @Query("""
        SELECT t.seat.id
        FROM Ticket t
        WHERE t.screening.id = :screeningId
        """)
    List<Integer> findOccupiedSeatIds(@Param("screeningId") Integer screeningId);

    @Query("""
    SELECT COUNT(t) > 0
    FROM Ticket t
    JOIN t.booking b
    JOIN b.user u
    JOIN t.screening s
    JOIN s.film f
    WHERE u.id = :userId
    AND f.id = :filmId
""")
    boolean existsTicketForUserAndFilm(Integer userId, Integer filmId);
}