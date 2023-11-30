package com.company.explorecalifornia.repository;

import com.company.explorecalifornia.domain.TourPackage;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.Optional;

/**
 * The interface {@code TourPackageRepository} specifies methods for operating on the TourPackage entity in the database
 */
public interface TourPackageRepository extends JpaRepository<TourPackage, Long> {

    /**
     * Retrieves a tour package from the database by tour package name
     * @param name the tour package name
     * @return the tour package
     */
    Optional<TourPackage> findByName(String name);

    /**
     * Retrieves a tour package from the database by tour package code
     * @param code the tour package code
     * @return the tour package
     */
    Optional<TourPackage> findByCode(String code);

    /**
     * Retrieves all tour packages from the database
     * @param pageable the pageable
     * @return the tour package
     */
    Page<TourPackage> findAll(Pageable pageable);
}