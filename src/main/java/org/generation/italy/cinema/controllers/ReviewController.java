package org.generation.italy.cinema.controllers;

import org.generation.italy.cinema.model.entities.Review;
import org.generation.italy.cinema.model.services.abstractions.iReviewService;
import org.springframework.web.bind.annotation.*;

import java.security.Principal;
import java.util.List;

@RestController
@RequestMapping("/api/reviews")
public class ReviewController {
    private final iReviewService reviewService;

    public ReviewController(iReviewService reviewService) {
        this.reviewService = reviewService;
    }

    @PostMapping("/{filmId}")
    public Review createReview(@PathVariable Integer filmId, @RequestBody Review review, Principal principal) {
        String username = principal.getName();
        return reviewService.createReview(username, filmId, review);
    }

    @GetMapping("/{filmId}")
    public List<Review> getReviewsByFilm(@PathVariable Integer filmId){
        return reviewService.findByFilmId(filmId);
    }
}
