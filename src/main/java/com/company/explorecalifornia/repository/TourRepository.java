package com.company.explorecalifornia.repository;

import com.company.explorecalifornia.domain.Difficulty;
import com.company.explorecalifornia.domain.Tour;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.List;
import java.util.Optional;

/**
 * The interface {@code TourRepository} specifies methods for operating on the Tour entity in the database
 */
public interface TourRepository extends JpaRepository<Tour, Long> {

    /**
     * Retrieves a tour from the database by difficulty
     * @param difficulty the difficulty
     * @param pageable the pageable
     * @return the tour
     */
    List<Tour> findByDifficulty(Difficulty difficulty, Pageable pageable);

    /**
     * Retrieves a tour from the database by tour package code
     * @param code the tour package code
     * @param pageable the pageable
     * @return the tour
     */
    List<Tour> findByTourPackageCode(String code, Pageable pageable);

    /**
     * Retrieves all tours from the database
     *
     * @param pageable the pageable
     * @return the list of tours
     */
    Page<Tour> findAll(Pageable pageable);

    /**
     * Retrieves a tour from the database by title
     *
     * @param title the title
     * @return the tour
     */
    Optional<Tour> findByTitleIgnoreCase(String title);
}