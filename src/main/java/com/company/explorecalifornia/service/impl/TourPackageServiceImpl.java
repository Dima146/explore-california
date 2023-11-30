package com.company.explorecalifornia.service.impl;

import com.company.explorecalifornia.domain.TourPackage;
import com.company.explorecalifornia.exception.DuplicateEntityException;
import com.company.explorecalifornia.exception.NoSuchEntityException;
import com.company.explorecalifornia.repository.TourPackageRepository;
import com.company.explorecalifornia.service.PageableCreator;
import com.company.explorecalifornia.service.TourPackageService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import java.util.List;
import java.util.Optional;

/**
 * The class {@code TourPackageServiceImpl} is an implementation of the {@link TourPackageService} interface
 * and is designed to handle objects of the {@link TourPackage} class.
 *
 */

@Service
public class TourPackageServiceImpl implements TourPackageService {

    private final TourPackageRepository tourPackageRepository;
    private static final Logger LOGGER = LoggerFactory.getLogger(TourPackageServiceImpl.class);

    @Autowired
    public TourPackageServiceImpl(TourPackageRepository tourPackageRepository) {
        this.tourPackageRepository = tourPackageRepository;
    }

    @Override
    public TourPackage findById(Long id) {
        return tourPackageRepository.findById(id).orElseThrow(() ->
                        new NoSuchEntityException("Tour package with id " + id + " does not exist"));
    }

    @Override
    public TourPackage findByCode(String code) {
        return tourPackageRepository.findByCode(code).orElseThrow(() ->
                new NoSuchEntityException("Tour package with the code " + code + " does not exist"));
    }

    @Override
    public List<TourPackage> findAll(int page, int size) {
        Pageable pageable = PageableCreator.createPageRequest(page, size);
        return tourPackageRepository.findAll(pageable).toList();
    }

    @Override
    @Transactional
    public TourPackage createTourPackage(TourPackage tourPackage) {
        String name = tourPackage.getName();
        LOGGER.info("Create a new tour package with the name {}", name);
        Optional<TourPackage> existingPackage = tourPackageRepository.findByName(name);
        if (existingPackage.isPresent()) {
            throw new DuplicateEntityException("Tour package with the name " + name +
                                               " already exists");
        }
        return tourPackageRepository.save(tourPackage);
    }

    @Override
    @Transactional
    public TourPackage updateTourPackage(Long id, TourPackage tourPackage) {
        LOGGER.info("Update the tour package with id {}", id);
        Optional<TourPackage> existingPackageById = tourPackageRepository.findById(id);
        if (existingPackageById.isEmpty()) {
            throw new NoSuchEntityException("Tour package with id " + id + " does not exist");
        }
        String name = tourPackage.getName();
        Optional<TourPackage> existingPackageByName = tourPackageRepository.findByName(name);
        if (existingPackageByName.isPresent()) {
            throw new DuplicateEntityException("Tour package with the name " + name + " already exists");
        }
        return tourPackageRepository.save(tourPackage);
    }

    @Override
    @Transactional
    public void deleteTourPackage(Long id) {
        LOGGER.info("Delete the tour package with id {}", id);
        Optional<TourPackage> existingPackage = tourPackageRepository.findById(id);
        if (existingPackage.isEmpty()) {
            throw new NoSuchEntityException("Tour package with id " + id + " does not exist");
        }
        tourPackageRepository.deleteById(id);
    }
}