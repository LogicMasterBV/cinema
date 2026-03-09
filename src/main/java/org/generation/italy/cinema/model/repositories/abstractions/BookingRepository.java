package org.generation.italy.cinema.model.repositories.abstractions;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.generation.italy.cinema.model.entities.Booking;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface BookingRepository extends JpaRepository<Booking, Integer> {

    @Query("""
SELECT b
FROM Booking b
JOIN FETCH b.screening s
JOIN FETCH s.film f
LEFT JOIN FETCH b.tickets t
LEFT JOIN FETCH t.seat seat
WHERE b.user.id = :userId
""")
    List<Booking> findDetailedBookingsByUser(@Param("userId") Integer userId);
    List<Booking> findByUser_Id(Integer userId); // Tutte le prenotazioni di un utente (DB)
    List<Booking> findByScreening_Id(Integer screeningId); // Tutte le prenotazioni per una proiezione (DB)
}
