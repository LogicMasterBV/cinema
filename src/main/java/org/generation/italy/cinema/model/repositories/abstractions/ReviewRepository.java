package org.generation.italy.cinema.model.repositories.abstractions;

import org.generation.italy.cinema.model.entities.Review;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ReviewRepository extends JpaRepository<Review, Integer> {
List<Review> findByFilmId(Integer filmId);
boolean existsByUserIdAndFilmId(Integer userId, Integer filmId);
}
