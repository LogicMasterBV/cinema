package org.generation.italy.cinema.model.services.implementations;

import jakarta.transaction.Transactional;
import org.generation.italy.cinema.dto.BookingDetailsDTO;
import org.generation.italy.cinema.dto.SeatInfoDTO;
import org.generation.italy.cinema.model.entities.Booking;
import org.generation.italy.cinema.model.entities.Screening;
import org.generation.italy.cinema.model.entities.User;
import org.generation.italy.cinema.model.repositories.abstractions.BookingRepository;
import org.generation.italy.cinema.model.repositories.abstractions.ScreeningRepository;
import org.generation.italy.cinema.model.repositories.abstractions.UserRepository;
import org.generation.italy.cinema.model.services.abstractions.iBookingService;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class BookingServiceJpa implements iBookingService {

    private final BookingRepository bookingRepo; // Salva, recupera e cancella proiezioni
    private final UserRepository userRepo;  // Verifica che l'utente esista e recupera l'entità user corretta
    private final ScreeningRepository screeningRepo; // Verifica che la proiezione esista e recupera la proiezione corretta

    public BookingServiceJpa(BookingRepository bookingRepo, UserRepository userRepo, ScreeningRepository screeningRepo) {
        this.bookingRepo = bookingRepo;
        this.userRepo = userRepo;
        this.screeningRepo = screeningRepo;
    }

    @Override
    public List<Booking> findAll() {
        return bookingRepo.findAll();
    }

    @Override
    public Optional<Booking> findById(Integer id) {
        return bookingRepo.findById(id);
    }

    @Override
    public List<Booking> findByUser(Integer userId) {
        return bookingRepo.findByUser_Id(userId);
    }

    @Override
    public List<Booking> findByScreening(Integer screeningId) {
        return bookingRepo.findByScreening_Id(screeningId);
    }

    @Override
    // Operazione di SCRITTURA sul DB (messo x controlli miei) --> sulle Letture no TRANSACTIONAL
    @Transactional // il metodo create è una transazione a parte. Se i primi 2 controlli non vanno non arriviamo al save. Se il .save non funziona andiamo in rollback
    public Booking create(Booking booking) {

        // controllo esistenza utente
        User user = userRepo.findById(booking.getUser().getId())
                .orElseThrow(() -> new RuntimeException("User not found")); // Se non lo trova lancia un eccezione

        // controllo esistenza proiezioni
        Screening screening = screeningRepo.findById(booking.getScreening().getId())
                .orElseThrow(() -> new RuntimeException("Screening not found")); // Se non lo trova lancia un eccezione

        booking.setUser(user);
        booking.setScreening(screening);

        return bookingRepo.save(booking);
    }

    @Override
    public boolean deleteById(Integer id) {
        if (!bookingRepo.existsById(id)) {
            return false;
        }
        bookingRepo.deleteById(id);
        return true;
    }
    @Override
    public List<BookingDetailsDTO> findBookingDetailsByUser(Integer userId) {

        List<Booking> bookings = bookingRepo.findDetailedBookingsByUser(userId);

        return bookings.stream().map(b -> {

            List<SeatInfoDTO> seats = b.getTickets().stream()
                    .map(t -> new SeatInfoDTO(
                            t.getSeat().getId(),
                            t.getSeat().getRowNumber(),
                            t.getSeat().getSeatNumber()))
                    .toList();

            return new BookingDetailsDTO(
                    b.getId(),
                    b.getScreening().getFilm().getTitle(),
                    b.getScreening().getScreeningDate(),
                    b.getScreening().getScreeningTime(),
                    b.getTotalPrice(),
                    seats
            );

        }).toList();
    }
}
