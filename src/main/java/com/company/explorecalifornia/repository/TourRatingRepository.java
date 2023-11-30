package com.company.explorecalifornia.repository;

import com.company.explorecalifornia.domain.TourRating;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import java.util.List;
import java.util.Optional;

/**
 * The interface {@code TourRatingRepository} specifies methods for operating on the TourRating entity in the database
 */
public interface TourRatingRepository extends JpaRepository<TourRating, Long> {

    /**
     * Retrieves a tour rating from the database by tour id
     * @param tourId the tour id
     * @param pageable the pageable
     * @return the tour rating
     */
    List<TourRating> findByTourId(Long tourId, Pageable pageable);

    /**
     * Retrieves a tour rating from the database by user id
     * @param userId the user id
     * @param pageable the pageable
     * @return the tour rating
     */
    List<TourRating> findByUserId(Long userId, Pageable pageable);

    /**
     * Retrieves a tour rating from the database by user id and tour id
     * @param userId the user id
     * @param tourId the user id
     * @return the tour rating
     */
    Optional<TourRating> findByUserIdAndTourId(Long userId, Long tourId);

    /**
     * Removes a tour rating from the database by tour rating id
     * @param id the tour rating id
     */
    void deleteById(Long id);

    /**
     * Calculates the average score of a tour
     * @param tourId the tour id
     * @return the average score
     */
    @Query("SELECT AVG(tr.score) FROM TourRating tr WHERE tr.tour.id=:tourId")
    Double getAverageScore(Long tourId);
}