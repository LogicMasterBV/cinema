package org.generation.italy.cinema.model.services.implementations;

import jakarta.transaction.Transactional;
import org.generation.italy.cinema.model.entities.BookedService;
import org.generation.italy.cinema.model.entities.BookedServiceId;
import org.generation.italy.cinema.model.entities.Booking;
import org.generation.italy.cinema.model.entities.CinemaService;
import org.generation.italy.cinema.model.repositories.abstractions.BookedServiceRepository;
import org.generation.italy.cinema.model.repositories.abstractions.BookingRepository;
import org.generation.italy.cinema.model.repositories.abstractions.CinemaServiceRepository;
import org.generation.italy.cinema.model.services.abstractions.iBookedService;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class BookedServiceJpa implements iBookedService {

    private final BookedServiceRepository repository;
    private final BookingRepository bookingRepository;
    private final CinemaServiceRepository cinemaServiceRepository;

    public BookedServiceJpa(BookedServiceRepository repository,
                            BookingRepository bookingRepository,
                            CinemaServiceRepository cinemaServiceRepository) {
        this.repository = repository;
        this.bookingRepository = bookingRepository;
        this.cinemaServiceRepository = cinemaServiceRepository;
    }

    // READ

    @Override
    public List<BookedService> findAll() {
        return repository.findAll();
    }

    @Override
    public Optional<BookedService> findById(BookedServiceId id) {
        return repository.findById(id);
    }

    @Override
    public List<BookedService> findByBooking(Integer bookingId) {
        return repository.findByBooking_Id(bookingId);
    }

    @Override
    public List<BookedService> findByService(Integer serviceId) {
        return repository.findByService_Id(serviceId);
    }

    //  CREATE

    @Override
    @Transactional
    public BookedService create(BookedService bookedService) {

        // Controllo esistenza Booking
        Booking booking = bookingRepository.findById(
                        bookedService.getBooking().getId())
                .orElseThrow(() -> new RuntimeException("Booking not found"));

        // Controllo esistenza ExtraProduct (cibo/bevanda)
        CinemaService service = cinemaServiceRepository.findById(
                        bookedService.getService().getId())
                .orElseThrow(() -> new RuntimeException("Service not found"));

        // Imposto entità gestite da JPA
        bookedService.setBooking(booking);
        bookedService.setService(service);

        return repository.save(bookedService);
    }

    // DELETE

    @Override
    public boolean delete(BookedServiceId id) {

        if (!repository.existsById(id)) {
            return false;
        }

        repository.deleteById(id);
        return true;
    }
}
