package org.generation.italy.cinema.model.repositories.abstractions;

import org.generation.italy.cinema.model.entities.Seat;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface SeatRepository extends JpaRepository<Seat, Integer> {

    // Trova tutti i posti di una sala ordinati per fila e numero
    @Query("""
            SELECT s
            FROM Seat s
            WHERE s.hall.id = :hallId
            ORDER BY s.rowNumber ASC, s.seatNumber ASC
            """)
    List<Seat> findSeatsByHall(@Param("hallId") Integer hallId);

    // Controlla se esiste già un posto con stessa sala, fila e numero
    @Query("""
            SELECT COUNT(s) > 0
            FROM Seat s
            WHERE s.hall.id = :hallId
              AND s.rowNumber = :rowNumber
              AND s.seatNumber = :seatNumber
            """)
    boolean seatExists(@Param("hallId") Integer hallId,
                       @Param("rowNumber") Integer rowNumber,
                       @Param("seatNumber") Integer seatNumber);

    // Conta quanti posti ha una determinata sala
    @Query("""
            SELECT COUNT(s)
            FROM Seat s
            WHERE s.hall.id = :hallId
            """)
    long countSeatsByHall(@Param("hallId") Integer hallId);
}

