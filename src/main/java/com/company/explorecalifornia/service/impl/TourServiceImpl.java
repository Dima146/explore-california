package com.company.explorecalifornia.service.impl;

import com.company.explorecalifornia.domain.Difficulty;
import com.company.explorecalifornia.domain.Region;
import com.company.explorecalifornia.domain.Tour;
import com.company.explorecalifornia.domain.TourPackage;
import com.company.explorecalifornia.exception.DuplicateEntityException;
import com.company.explorecalifornia.exception.NoSuchEntityException;
import com.company.explorecalifornia.repository.RegionRepository;
import com.company.explorecalifornia.repository.TourPackageRepository;
import com.company.explorecalifornia.repository.TourRepository;
import com.company.explorecalifornia.service.PageableCreator;
import com.company.explorecalifornia.service.TourService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import java.util.List;
import java.util.Optional;

/**
 * The class {@code TourServiceImpl} is an implementation of the {@link TourService} interface
 * and is designed to handle objects of the {@link Tour} class.
 *
 */

@Service
public class TourServiceImpl implements TourService {

    private final TourRepository tourRepository;
    private final TourPackageRepository tourPackageRepository;
    private final RegionRepository regionRepository;

    private static final Logger LOGGER = LoggerFactory.getLogger(TourRatingServiceImpl.class);


    @Autowired
    public TourServiceImpl(TourRepository tourRepository, TourPackageRepository tourPackageRepository,
                           RegionRepository regionRepository) {
        this.tourRepository = tourRepository;
        this.tourPackageRepository = tourPackageRepository;
        this.regionRepository = regionRepository;
    }

    @Override
    public Tour findById(Long id) {
        return tourRepository.findById(id).orElseThrow(() ->
                new NoSuchEntityException("Tour with id " + id + " does not exist"));
    }

    @Override
    public List<Tour> findByDifficulty(Difficulty difficulty, int page, int size) {
        Pageable pageable = PageableCreator.createPageRequest(page, size);
        return tourRepository.findByDifficulty(difficulty, pageable);
    }

    @Override
    public List<Tour> findByTourPackageCode(String code, int page, int size) {
        Pageable pageable = PageableCreator.createPageRequest(page, size);
        return tourRepository.findByTourPackageCode(code, pageable);
    }

    @Override
    public List<Tour> findAll(int page, int size) {
        Pageable pageable = PageableCreator.createPageRequest(page, size);
        return tourRepository.findAll(pageable).toList();
    }

    @Override
    @Transactional
    public Tour createTour(Tour tour) {
        String title = tour.getTitle();
        LOGGER.info("Create a new tour with the title {}", title);
        Optional<Tour> existingTour = tourRepository.findByTitleIgnoreCase(title);
        if (existingTour.isPresent()) {
            throw new DuplicateEntityException("Tour with the title " + title + " already exists");
        }
        Region region = findRegion(tour.getRegion().getName());
        tour.setRegion(region);
        TourPackage tourPackage = findTourPackage(tour.getTourPackage().getCode());
        tour.setTourPackage(tourPackage);

        return tourRepository.save(tour);
    }

    @Override
    @Transactional
    public Tour updateTour(Long id, Tour tour) {
        LOGGER.info("Update the tour with id {}", id);
        Optional<Tour> existingTourById = tourRepository.findById(id);
        if (existingTourById.isEmpty()) {
            throw new NoSuchEntityException("Tour with id " + id + " does not exist");
        }
        Region region = findRegion(tour.getRegion().getName());
        tour.setRegion(region);
        TourPackage tourPackage = findTourPackage(tour.getTourPackage().getCode());
        tour.setTourPackage(tourPackage);

        return tourRepository.save(tour);
    }

    @Override
    @Transactional
    public void deleteTour(Long id) {
        LOGGER.info("Delete the tour with id {}", id);
        Optional<Tour> existingTour = tourRepository.findById(id);
        if (existingTour.isEmpty()) {
            throw new NoSuchEntityException("Tour with id " + id + " does not exist");
        }
        tourRepository.deleteById(id);
    }

    private Region findRegion(String name) {
        Optional<Region> existingRegion = regionRepository.findByName(name);
        if (existingRegion.isPresent()) {
            return existingRegion.get();
        } else {
            Region newRegion = new Region(name);
            return regionRepository.save(newRegion);
        }
    }

    private TourPackage findTourPackage(String code) {
        return tourPackageRepository.findByCode(code).orElseThrow(
                () -> new NoSuchEntityException("A tour package with the code " + code + " does not exist. " +
                        "Create a new package with this code."));
    }
}