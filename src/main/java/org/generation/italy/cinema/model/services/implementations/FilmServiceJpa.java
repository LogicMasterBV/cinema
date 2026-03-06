package org.generation.italy.cinema.model.services.implementations;

import org.generation.italy.cinema.model.entities.Booking;
import org.generation.italy.cinema.model.entities.Film;
import org.generation.italy.cinema.model.entities.Genre;
import org.generation.italy.cinema.model.entities.Screening;
import org.generation.italy.cinema.model.repositories.abstractions.BookingRepository;
import org.generation.italy.cinema.model.repositories.abstractions.FilmRepository;
import org.generation.italy.cinema.model.services.abstractions.iFilmService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;

@Service
public class FilmServiceJpa implements iFilmService {

    private FilmRepository repository;
    private BookingRepository bookingRepository;

    @Autowired
    public FilmServiceJpa(FilmRepository repository, BookingRepository bookingRepository ) {
        this.repository = repository;
        this.bookingRepository = bookingRepository;
    }

    @Override
    public List<Film> findFilmAll() {
        return repository.findAll();
    }

    @Override
    public Optional<Film> findFilmById(int id) {
        return repository.findById(id);
    }

    @Override
    public boolean deleteFilmById(int id) {
        Optional<Film> of = repository.findById(id);
        if(of.isPresent()){
            repository.deleteById(id);
            return true;
        }
        return false;
    }

    @Override
    public Film createFilm(Film film) {
        return repository.save(film);
    }

    @Override
    public boolean updateFilmById(Film film) {
        boolean update = repository.existsById(film.getId());
        if(film.getId() == null || !update){
            return false;
        }
        repository.save(film);
        return true;
    }

    @Override
    public List<Film> findFilmByDateScreening(LocalDate date) {
        return repository.findFilmByDateScreening(date);
    }

    @Override
    public Page<Film> globalSearch(String query, Pageable pageable) {
        return repository.globalSearch(query, pageable);
    }

    @Override
    public List<Film> getSuggestedFilmsForUser(Integer userId) {
        // 1. prendo tutti i booking dell'utente
        List<Booking> bookings = bookingRepository.findByUser_Id(userId);

        // 2. estraggo i generi dai film già visti
        Set<String> preferredGenres = bookings.stream()
                .map(Booking::getScreening)
                .map(Screening::getFilm)
                .flatMap(film -> film.getGenres().stream())
                .map(Genre::getName)
                .collect(Collectors.toSet());

        // 3. se non ha booking restituisco lista vuota
        if (preferredGenres.isEmpty()) {
            return List.of();
        }

        // 4. cerco film in uscita nelle prossime 4 settimane con quei generi
        LocalDate today = LocalDate.now();
        LocalDate inFourWeeks = today.plusWeeks(4);
        return repository.findUpcomingByGenres(preferredGenres, today, inFourWeeks);
    }
}
