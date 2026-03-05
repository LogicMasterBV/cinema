package org.generation.italy.cinema.model.services.implementations;

import org.generation.italy.cinema.model.entities.Film;
import org.generation.italy.cinema.model.entities.Review;
import org.generation.italy.cinema.model.entities.User;
import org.generation.italy.cinema.model.repositories.abstractions.FilmRepository;
import org.generation.italy.cinema.model.repositories.abstractions.ReviewRepository;
import org.generation.italy.cinema.model.repositories.abstractions.TicketRepository;
import org.generation.italy.cinema.model.repositories.abstractions.UserRepository;
import org.generation.italy.cinema.model.services.abstractions.iReviewService;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ReviewService implements iReviewService {
    private final ReviewRepository reviewRepository;
    private final TicketRepository ticketRepository;
    private final UserRepository userRepository;
    private final FilmRepository filmRepository;

    public ReviewService(ReviewRepository reviewRepository, TicketRepository ticketRepository, UserRepository userRepository, FilmRepository filmRepository) {
        this.reviewRepository = reviewRepository;
        this.ticketRepository = ticketRepository;
        this.userRepository = userRepository;
        this.filmRepository = filmRepository;
    }


    @Override
    public Review createReview(String username, Integer filmId, Review review) {

        User user = userRepository.findByEmailIgnoreCase(username)
                .orElseThrow(() -> new RuntimeException("Utente non trovato"));

        Film film = filmRepository.findById(filmId)
                .orElseThrow(() -> new RuntimeException("Film non trovato"));

        boolean seenFilm =
                ticketRepository.existsTicketForUserAndFilm(user.getId(), filmId);

        if(!seenFilm){
            throw new RuntimeException("L'utente non ha visto quel film.");
        }

        if(reviewRepository.existsByUserIdAndFilmId(user.getId(), filmId)){
            throw new RuntimeException("Hai già recensito questo film");
        }

        review.setUser(user);
        review.setFilm(film);

        return reviewRepository.save(review);
    }

    @Override
    public List<Review> findByFilmId(Integer filmId) {
        return reviewRepository.findByFilmId(filmId);
    }




}
