package org.generation.italy.cinema.model.services.implementations;

import jakarta.persistence.EntityNotFoundException;
import org.generation.italy.cinema.model.entities.Screening;
import org.generation.italy.cinema.model.entities.Seat;
import org.generation.italy.cinema.model.repositories.abstractions.FilmRepository;
import org.generation.italy.cinema.model.repositories.abstractions.HallRepository;
import org.generation.italy.cinema.model.repositories.abstractions.ScreeningRepository;
import org.generation.italy.cinema.model.services.abstractions.iScreeningService;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;

@Service
public class ScreeningService implements iScreeningService {

    private final ScreeningRepository screeningRepository;
    private final HallRepository hallRepository;
    private final FilmRepository filmRepository;

    public ScreeningService(ScreeningRepository screeningRepository,
                                HallRepository hallRepository,
                                FilmRepository filmRepository) {
        this.screeningRepository = screeningRepository;
        this.hallRepository = hallRepository;
        this.filmRepository = filmRepository;
    }

    @Override
    public List<Screening> getAll() {
        return screeningRepository.findAll();
    }

    @Override
    public Screening getById(Integer id) {
        return screeningRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Proiezione non trovata con id: " + id));
    }

    @Override
    public List<Screening> getByFilm(Integer filmId) {
        return screeningRepository.findByFilm_Id(filmId);
    }

    @Override
    public List<Screening> getByDate(LocalDate date) {
        return screeningRepository.findByScreeningDate(date);
    }

    @Override
    public List<Screening> getByDateBetween(LocalDate from, LocalDate to) {
        if (from.isAfter(to)) {
            throw new IllegalArgumentException("La data non è valida.");
        }
        return screeningRepository.findByScreeningDateBetween(from, to);
    }

    @Override
    public List<Screening> getByHall(Integer hallId) {
        return screeningRepository.findByHall_Id(hallId);
    }

    @Override
    public List<Screening> getByHallAndDate(Integer hallId, LocalDate date) {
        return screeningRepository.findByHall_IdAndScreeningDate(hallId, date);
    }

    @Override
    public List<Seat> getAvailableSeats(Integer screeningId) {
        Screening screening = getById(screeningId);
        return screeningRepository.findAvailableSeats(screeningId, screening.getHall().getId());
    }

    @Override
    public long getOccupiedSeats(Integer screeningId) {
        getById(screeningId);
        return screeningRepository.countOccupiedSeats(screeningId);
    }

    @Override
    public Screening create(Screening screen) {
        // Verifica che la sala esista
        hallRepository.findById(screen.getHall().getId())
                .orElseThrow(() -> new EntityNotFoundException("Sala non trovata"));

        // Verifica che il film esista
        filmRepository.findById(screen.getFilm().getId())
                .orElseThrow(() -> new EntityNotFoundException("Film non trovato"));

        // Controlla se ci sta già una proiezione
        boolean alreadyExist = screeningRepository.existsByHall_IdAndScreeningDateAndScreeningTime(
                screen.getHall().getId(),
                screen.getScreeningDate(),
                screen.getScreeningTime()
        );
        if (alreadyExist) {
            throw new IllegalStateException("Esiste già una proiezione in questa sala a quest'ora");
        }

        return screeningRepository.save(screen);
    }

    @Override
    public Screening update(Integer id, Screening screen) {
        Screening existing = getById(id);

        // Controlla se la proiezione attuale sia diversa da quella in cui la vogliamo aggiornare
        boolean sameHall = existing.getHall().getId().equals(screen.getHall().getId());
        boolean sameDate = existing.getScreeningDate().equals(screen.getScreeningDate());
        boolean sameTime = existing.getScreeningTime().equals(screen.getScreeningTime());

        if (!sameHall || !sameDate || !sameTime) { //controlla se almeno uno dei 3 campi sia diverso (
            boolean alreadyExist = screeningRepository.existsByHall_IdAndScreeningDateAndScreeningTime(
                    screen.getHall().getId(),
                    screen.getScreeningDate(),
                    screen.getScreeningTime()
            );
            if (alreadyExist) {
                throw new IllegalStateException("Esiste già una proiezione in questa sala a quest'ora");
            }
        }

        existing.setFilm(screen.getFilm());
        existing.setHall(screen.getHall());
        existing.setScreeningDate(screen.getScreeningDate());
        existing.setScreeningTime(screen.getScreeningTime());
        // aggiungi altri campi che hai nell'entità

        return screeningRepository.save(existing);
    }

    @Override
    public void delete(Integer id) {
        Screening screening = getById(id);
        screeningRepository.delete(screening);
    }
}
