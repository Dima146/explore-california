package com.company.explorecalifornia.web;

import com.company.explorecalifornia.domain.TourRating;
import com.company.explorecalifornia.hateaos.HateoasProvider;
import com.company.explorecalifornia.service.TourRatingService;
import com.company.explorecalifornia.web.dto.TourRatingDto;
import com.company.explorecalifornia.web.dto.converter.DtoConverter;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.List;
import java.util.stream.Collectors;

/**
 * The class {@code TourRatingController} is a controller which contains REST endpoints
 * for performing operations on tour ratings.
 *
 */
@RestController
@RequestMapping(path = "/tour-ratings")
public class TourRatingController {

    private final TourRatingService tourRatingService;
    private final DtoConverter<TourRating, TourRatingDto> dtoConverter;
    private final HateoasProvider<TourRatingDto> hateoasProvider;

    @Autowired
    public TourRatingController(TourRatingService tourRatingService,
                                DtoConverter<TourRating, TourRatingDto> dtoConverter,
                                HateoasProvider<TourRatingDto> hateoasProvider) {
        this.tourRatingService = tourRatingService;
        this.dtoConverter = dtoConverter;
        this.hateoasProvider = hateoasProvider;
    }

    /**
     * Gets a tour rating by id
     *
     * @param id - the tour rating id
     * @return the tour rating with HATEOAS
     */
    @GetMapping("/{id}")
    @ResponseStatus(HttpStatus.OK)
    public TourRatingDto findById(@PathVariable Long id) {
        TourRating tourRating = tourRatingService.findById(id);
        TourRatingDto tourRatingDto = dtoConverter.convertToDto(tourRating);
        hateoasProvider.addLinks(tourRatingDto);
        return tourRatingDto;
    }

    /**
     * Gets a tour rating by tour id
     *
     * @param tourId - the tour id
     * @param page - the page number
     * @param size - the size of the page
     * @return the list of tour ratings with HATEOAS
     */
    @GetMapping("/tours/{tourId}")
    @ResponseStatus(HttpStatus.OK)
    public List<TourRatingDto> findByTourId(@PathVariable Long tourId,
                                         @RequestParam(value = "page", defaultValue = "0", required = false) int page,
                                         @RequestParam(value = "size", defaultValue = "5", required = false) int size) {
        List<TourRating> tourRatings = tourRatingService.findByTourId(tourId, page, size);
        return tourRatings.stream()
                .map(dtoConverter::convertToDto)
                .peek(hateoasProvider::addLinks)
                .collect(Collectors.toList());
    }

    /**
     * Gets a tour rating by user id
     *
     * @param userId - the user id
     * @param page - the page number
     * @param size - the size of the page
     * @return the list of tour ratings with HATEOAS
     */
    @GetMapping("/users/{userId}")
    @ResponseStatus(HttpStatus.OK)
    public List<TourRatingDto> findByUserId(@PathVariable Long userId,
                                         @RequestParam(value = "page", defaultValue = "0", required = false) int page,
                                         @RequestParam(value = "size", defaultValue = "5", required = false) int size) {
        List<TourRating> tourRatings = tourRatingService.findByUserId(userId, page, size);
        return tourRatings.stream()
                .map(dtoConverter::convertToDto)
                .peek(hateoasProvider::addLinks)
                .collect(Collectors.toList());
    }

    /**
     * Calculates the average score of a tour
     *
     * @param tourId - the tour id
     * @return the average score of the tour
     */
    @GetMapping("/tours/{tourId}/average")
    @ResponseStatus(HttpStatus.OK)
    public ResponseEntity<String> getAverageScore(@PathVariable Long tourId) {
        Double averageScore = tourRatingService.calculateAverageScore(tourId);
        return ResponseEntity.status(HttpStatus.OK)
                .body(String.format("Average score: %1.1f", averageScore));

    }

    /**
     * Creates a tour rating
     *
     * @param tourRatingDto - the tour rating to create
     * @return the tour rating with HATEOAS
     */
    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public TourRatingDto createTourRating(@RequestBody @Valid TourRatingDto tourRatingDto) {
        TourRating createdRating =
                tourRatingService.createTourRating(dtoConverter.convertToEntity(tourRatingDto));
        TourRatingDto ratingDto = dtoConverter.convertToDto(createdRating);
        hateoasProvider.addLinks(ratingDto);
        return ratingDto;
    }

    /**
     * Updates a tour rating by id
     *
     * @param id - the user id
     * @param tourRatingDto - the tour rating to update
     * @return the updated tour rating with HATEOAS
     */
    @PutMapping("/{id}")
    @ResponseStatus(HttpStatus.OK)
    public TourRatingDto updateTourRating(@PathVariable Long id, @RequestBody @Valid TourRatingDto tourRatingDto) {
        TourRating updatedRating = tourRatingService
                .updateTourRating(id, dtoConverter.convertToEntity(tourRatingDto));
        TourRatingDto ratingDto = dtoConverter.convertToDto(updatedRating);
        hateoasProvider.addLinks(ratingDto);
        return ratingDto;
    }

    /**
     * Deletes a tour rating by id
     *
     * @param id - the tour rating id
     * @return the list of tour ratings
     */
    @DeleteMapping("/{id}")
    public ResponseEntity<String> deleteTourPackage(@PathVariable Long id) {
        tourRatingService.deleteTourRating(id);
        return ResponseEntity.status(HttpStatus.OK)
                .body(String.format("Tour rating with %d has been deleted", id));
    }
}