package com.company.explorecalifornia.service.impl;

import com.company.explorecalifornia.domain.Tour;
import com.company.explorecalifornia.domain.TourRating;
import com.company.explorecalifornia.domain.User;
import com.company.explorecalifornia.exception.DuplicateEntityException;
import com.company.explorecalifornia.exception.NoSuchEntityException;
import com.company.explorecalifornia.repository.TourRatingRepository;
import com.company.explorecalifornia.repository.TourRepository;
import com.company.explorecalifornia.repository.UserRepository;
import com.company.explorecalifornia.service.PageableCreator;
import com.company.explorecalifornia.service.TourRatingService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import java.util.List;
import java.util.Optional;

/**
 * The class {@code TourRatingServiceImpl} is an implementation of the {@link TourRatingService} interface
 * and is designed to handle objects of the {@link TourRating} class.
 *
 */

@Service
public class TourRatingServiceImpl implements TourRatingService {

    private final TourRatingRepository tourRatingRepository;
    private final TourRepository tourRepository;
    private final UserRepository userRepository;

    private static final Logger LOGGER = LoggerFactory.getLogger(TourRatingServiceImpl.class);

    @Autowired
    public TourRatingServiceImpl(TourRatingRepository tourRatingRepository, TourRepository tourRepository,
                                 UserRepository userRepository) {
        this.tourRatingRepository = tourRatingRepository;
        this.tourRepository = tourRepository;
        this.userRepository = userRepository;
    }

    @Override
    public TourRating findById(Long id) {
        return tourRatingRepository.findById(id).orElseThrow(() ->
                new NoSuchEntityException("Tour rating with id " + id + " does not exist"));
    }

    @Override
    public List<TourRating> findByTourId(Long tourId, int page, int size) {
        Pageable pageable = PageableCreator.createPageRequest(page, size);
        return tourRatingRepository.findByTourId(tourId, pageable);
    }

    @Override
    public List<TourRating> findByUserId(Long userId, int page, int size) {
        Pageable pageable = PageableCreator.createPageRequest(page, size);
        return tourRatingRepository.findByUserId(userId, pageable);
    }

    @Override
    @Transactional
    public TourRating createTourRating(TourRating tourRating) {
        Long userId = tourRating.getUser().getId();
        Long tourId = tourRating.getTour().getId();
        LOGGER.info("Create a new tour rating for tour with id {} from the user with id {}", tourId, userId);
        Optional<TourRating> existingRating = tourRatingRepository.findByUserIdAndTourId(userId, tourId);
        if (existingRating.isPresent()) {
            throw new DuplicateEntityException("Tour rating with user id " + userId + " and tour id " + tourId +
                    " already exists");
        }
        Tour tour = verifyTour(tourId);
        tourRating.setTour(tour);
        User user = verifyUser(userId);
        tourRating.setUser(user);
        return tourRatingRepository.save(tourRating);
    }

    @Override
    @Transactional
    public TourRating updateTourRating(Long id, TourRating tourRating) {
        LOGGER.info("Update the tour rating with id {}", id);
        Optional<TourRating> existingRating = tourRatingRepository.findById(id);
        if (existingRating.isEmpty()) {
            throw new NoSuchEntityException("Tour rating with id " + id + " does not exist");
        }
        Long userId = tourRating.getUser().getId();
        Long tourId = tourRating.getTour().getId();
        Tour tour = verifyTour(tourId);
        User user = verifyUser(userId);
        tourRating.setTour(tour);
        tourRating.setUser(user);
        return tourRatingRepository.save(tourRating);
    }

    @Override
    @Transactional
    public void deleteTourRating(Long id) {
        LOGGER.info("Delete the tour rating with id {}", id);
        Optional<TourRating> tourRating = tourRatingRepository.findById(id);
        if (tourRating.isEmpty()) {
            throw new NoSuchEntityException("Tour rating with id " + id + " does not exist");
        }
        tourRatingRepository.deleteById(id);
    }

    @Override
    public Double calculateAverageScore(Long tourId) {
        LOGGER.info("Calculate the rating score for the tour with id {}", tourId);
        Tour tour = verifyTour(tourId);
        return tourRatingRepository.getAverageScore(tour.getId());
    }

    private Tour verifyTour(Long tourId) {
        Optional<Tour> tour = tourRepository.findById(tourId);
        if (tour.isEmpty()) {
            throw new NoSuchEntityException("Tour with id " + tourId + " was not found");
        }
        return tour.get();
    }

    private User verifyUser(Long userId) {
        Optional<User> user = userRepository.findById(userId);
        if (user.isEmpty()) {
            throw new NoSuchEntityException("User with id " + userId + " was not found");
        }
        return user.get();
    }
}