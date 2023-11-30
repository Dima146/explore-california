package com.company.explorecalifornia.service;

import com.company.explorecalifornia.domain.Difficulty;
import com.company.explorecalifornia.domain.Tour;
import java.util.List;

/**
 * The {@code TourService} interface specifies the behavior for handling {@link Tour} objects.
 */
public interface TourService {

    /**
     * Finds a tour by id
     *
     * @param id the tour id
     * @return the tour
     */
    Tour findById(Long id);

    /**
     * Finds a tour by difficulty
     *
     * @param difficulty the tour difficulty
     * @param page the page number
     * @param size the page size
     * @return the list of tours
     */
    List<Tour> findByDifficulty(Difficulty difficulty, int page, int size);

    /**
     * Finds a tour by tour package code
     *
     * @param code the tour package code
     * @param page the page number
     * @param size the page size
     * @return the list of tours
     */
    List<Tour> findByTourPackageCode(String code, int page, int size);

    /**
     * Finds all tours
     *
     * @param page the page number
     * @param size the page size
     * @return the list of tours
     */
    List<Tour> findAll(int page, int size);

    /**
     * Creates a new tour
     *
     * @param tour the tour to create
     * @return the created tour
     */
    Tour createTour(Tour tour);

    /**
     * Updates a tour
     *
     * @param id the tour id
     * @param tour the tour to update
     *
     * @return the updated tour
     */
    Tour updateTour(Long id, Tour tour);

    /**
     * Deletes a tour by id
     *
     * @param id the tour id
     */
    void deleteTour(Long id);
}