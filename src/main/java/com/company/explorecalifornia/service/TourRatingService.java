package com.company.explorecalifornia.service;

import com.company.explorecalifornia.domain.TourRating;
import java.util.List;

/**
 * The {@code TourRatingService} interface specifies the behavior for handling {@link TourRating} objects.
 */
public interface TourRatingService {

    /**
     * Finds a tour rating by id
     *
     * @param id the tour rating id
     * @return the tour rating
     */
    TourRating findById(Long id);

    /**
     * Finds a tour rating by tour id
     *
     * @param tourId the tour id
     * @return the list of tour ratings
     */
    List<TourRating> findByTourId(Long tourId, int page, int size);

    /**
     * Finds a tour rating by user id
     *
     * @param userId the user id
     * @return the list of tour ratings
     */
    List<TourRating> findByUserId(Long userId, int page, int size);

    /**
     * Updates a tour
     *
     * @param tourRating the tour rating to create
     *
     * @return the updated tour rating
     */
    TourRating createTourRating(TourRating tourRating);

    /**
     * Updates a tour
     *
     * @param id the tour rating id
     * @param tourRating the tour rating to update
     *
     * @return the updated tour rating
     */
    TourRating updateTourRating(Long id, TourRating tourRating);

    /**
     * Deletes a tour rating by id
     *
     * @param id the tour rating id
     */
    void deleteTourRating(Long id);

    /**
     * Calculates the average tour score
     *
     * @param tourId the tour id
     */
    Double calculateAverageScore(Long tourId);
}