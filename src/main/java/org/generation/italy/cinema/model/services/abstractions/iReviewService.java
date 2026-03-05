package org.generation.italy.cinema.model.services.abstractions;

import org.generation.italy.cinema.model.entities.Review;

import java.util.List;

public interface iReviewService {

    Review createReview(String username, Integer filmId, Review review);
    List<Review> findByFilmId(Integer filmId);

}