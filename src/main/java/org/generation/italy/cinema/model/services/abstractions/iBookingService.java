package org.generation.italy.cinema.model.services.abstractions;

import org.generation.italy.cinema.dto.BookingDTO;
import org.generation.italy.cinema.model.entities.Booking;

import java.util.List;
import java.util.Optional;

public interface iBookingService {

    List<Booking> findAll();

    Optional<Booking> findById(Integer id);

    List<Booking> findByUser(Integer userId);

    List<Booking> findByScreening(Integer screeningId);

    Booking create(Booking booking);

    boolean deleteById(Integer id);
}
