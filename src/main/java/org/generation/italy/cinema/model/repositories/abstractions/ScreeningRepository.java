package org.generation.italy.cinema.model.repositories.abstractions;

import org.generation.italy.cinema.model.entities.Screening;
import org.generation.italy.cinema.model.entities.Seat;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;

public interface ScreeningRepository extends JpaRepository<Screening, Integer> {
    List<Screening> findByFilm_Id(Integer filmId);
    List<Screening> findByScreeningDate(LocalDate date);
    List<Screening> findByScreeningDateBetween(LocalDate from, LocalDate to);
    List<Screening> findByHall_Id(Integer hallId);
    List<Screening> findByHall_IdAndScreeningDate(Integer hallId, LocalDate date);

   //cerca i posti disponibili
    @Query("""
        SELECT s FROM Seat s
        WHERE s.hall.id = :hallId
        AND s.id NOT IN (
            SELECT t.seat.id FROM Ticket t
            WHERE t.screening.id = :screeningId
        )
        ORDER BY s.rowNumber ASC, s.seatNumber ASC
    """)
    List<Seat> findAvailableSeats(@Param("screeningId") Integer screeningId,
                                  @Param("hallId") Integer hallId);

    //conta quanti posti sono occupati
    @Query("""
        SELECT COUNT(t) FROM Ticket t
        WHERE t.screening.id = :screeningId
    """)
    long countOccupiedSeats(@Param("screeningId") Integer screeningId);


    //controlla se ci sta già un proiezione in una sala in determinati giorni e orari
    boolean existsByHall_IdAndScreeningDateAndScreeningTime(Integer hallId,
                                                            LocalDate screeningDate,
                                                            LocalTime screeningTime);
}