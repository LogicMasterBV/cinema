package org.generation.italy.cinema.model.repositories.abstractions;

import org.generation.italy.cinema.model.entities.Booking;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface BookingRepository extends JpaRepository<Booking, Integer> {


    List<Booking> findByUser_Id(Integer userId); // Tutte le prenotazioni di un utente (DB)
    List<Booking> findByScreening_Id(Integer screeningId); // Tutte le prenotazioni per una proiezione (DB)
}
