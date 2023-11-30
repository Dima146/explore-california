package com.company.explorecalifornia.service;

import com.company.explorecalifornia.domain.TourPackage;
import java.util.List;

/**
 * The {@code TourPackageService} interface specifies the behavior for handling {@link TourPackage} objects.
 */
public interface TourPackageService {

    /**
     * Finds a tour package by id
     *
     * @param id the tour package id
     * @return the tour package
     */
    TourPackage findById(Long id);

    /**
     * Finds a tour package by code
     *
     * @param code the tour package code
     * @return the tour package
     */
    TourPackage findByCode(String code);

    /**
     * Finds all tour packages
     *
     * @param page the page number
     * @param size the page size
     * @return the list of tour packages
     */
    List<TourPackage> findAll(int page, int size);

    /**
     * Creates a new tour package
     *
     * @param tourPackage the tour package to create
     * @return the created tour package
     */
    TourPackage createTourPackage(TourPackage tourPackage);

    /**
     * Updates a tour package
     *
     * @param id the tour package id
     * @param tourPackage the tour package to update
     *
     * @return the updated tour package
     */
    TourPackage updateTourPackage(Long id, TourPackage tourPackage);

    /**
     * Deletes a tour package
     *
     * @param id the tour package id
     */
    void deleteTourPackage(Long id);
}