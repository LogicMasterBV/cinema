package org.generation.italy.cinema.model.repositories.abstractions;

import org.generation.italy.cinema.model.entities.BookingService;
import org.generation.italy.cinema.model.entities.BookingServiceId;
import org.springframework.data.jpa.repository.JpaRepository;

public interface BookingServiceRepository extends JpaRepository<BookingService, BookingServiceId> {
}
