package org.generation.italy.cinema.model.services.abstractions;

import org.generation.italy.cinema.model.entities.BookedService;
import org.generation.italy.cinema.model.entities.BookedServiceId;

import java.util.List;
import java.util.Optional;

public interface iBookedService {

    List<BookedService> findAll();

    Optional<BookedService> findById(BookedServiceId id);

    List<BookedService> findByBooking(Integer bookingId);

    List<BookedService> findByService(Integer serviceId);

    BookedService create(BookedService bookedService);

    boolean delete(BookedServiceId id);
}
