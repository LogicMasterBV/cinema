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

    List<Ticket> findByBookingId(Integer bookingId);

    Page<Ticket> findByScreeningId(Integer screeningId, Pageable pageable);

    @Query("""
        SELECT t
        FROM Ticket t
        JOIN t.booking b
        JOIN b.user u
        WHERE u.id = :userId
        ORDER BY t.date DESC
        """)
    Page<Ticket> findByUserId(@Param("userId") Integer userId, Pageable pageable);

    boolean existsByScreeningIdAndSeatId(Integer screeningId, Integer seatId);

    Optional<Ticket> findByScreeningIdAndSeatId(Integer screeningId, Integer seatId);

    @Query("""
        SELECT t.seat.id
        FROM Ticket t
        WHERE t.screening.id = :screeningId
        """)
    List<Integer> findOccupiedSeatIds(@Param("screeningId") Integer screeningId);
}