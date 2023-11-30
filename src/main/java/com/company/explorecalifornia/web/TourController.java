package com.company.explorecalifornia.web;

import com.company.explorecalifornia.domain.Difficulty;
import com.company.explorecalifornia.domain.Tour;
import com.company.explorecalifornia.hateaos.HateoasProvider;
import com.company.explorecalifornia.service.TourService;
import com.company.explorecalifornia.web.dto.TourDto;
import com.company.explorecalifornia.web.dto.converter.DtoConverter;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.List;
import java.util.stream.Collectors;

/**
 * The class {@code TourController} is a controller which contains REST endpoints
 * for performing operations on tours.
 *
 */
@RestController
@RequestMapping("/tours")
public class TourController {

    private final TourService tourService;
    private final DtoConverter<Tour, TourDto> dtoConverter;
    private final HateoasProvider<TourDto> hateoasProvider;


    @Autowired
    public TourController(TourService tourService, DtoConverter<Tour, TourDto> dtoConverter,
                          HateoasProvider<TourDto> hateoasProvider) {
        this.tourService = tourService;
        this.dtoConverter = dtoConverter;
        this.hateoasProvider = hateoasProvider;
    }

    /**
     * Gets a tour by tour id
     *
     * @param id the tour id
     * @return the tour with HATEOAS
    */
    @GetMapping("/{id}")
    @ResponseStatus(HttpStatus.OK)
    public TourDto findById(@PathVariable Long id) {
        Tour tour = tourService.findById(id);
        TourDto tourDto = dtoConverter.convertToDto(tour);
        hateoasProvider.addLinks(tourDto);
        return tourDto;
    }

    /**
     * Gets a tour by difficulty
     *
     * @param difficulty the difficulty
     * @param page the page number
     * @param size the size of the page
     * @return the list of tours with HATEOAS
    */
    @GetMapping(params = {"difficulty"})
    @ResponseStatus(HttpStatus.OK)
    public List<TourDto> findByDifficulty(@RequestParam Difficulty difficulty,
                                          @RequestParam(value = "page", defaultValue = "0", required = false) int page,
                                          @RequestParam(value = "size", defaultValue = "5", required = false) int size) {
        List<Tour> tours = tourService.findByDifficulty(difficulty, page, size);
        return tours.stream()
                .map(dtoConverter::convertToDto)
                .peek(hateoasProvider::addLinks)
                .collect(Collectors.toList());
    }

    /**
     * Gets a tour by tour package code
     *
     * @param code the tour package code
     * @param page the page number
     * @param size the size of the page
     * @return the list of tours with HATEOAS
    */
    @GetMapping(params = {"code"})
    @ResponseStatus(HttpStatus.OK)
    public List<TourDto> findByPackageCode(@RequestParam(name = "code") String code,
                                           @RequestParam(value = "page", defaultValue = "0", required = false) int page,
                                           @RequestParam(value = "size", defaultValue = "5", required = false) int size) {
        List<Tour> tours = tourService.findByTourPackageCode(code, page, size);
        return tours.stream()
                .map(dtoConverter::convertToDto)
                .peek(hateoasProvider::addLinks)
                .collect(Collectors.toList());
    }

    /**
     * Gets all tours
     *
     * @param page the page number
     * @param size the size of the page
     * @return the list of tours with HATEOAS
    */
    @GetMapping
    @ResponseStatus(HttpStatus.OK)
    public List<TourDto> findAllTours(@RequestParam(value = "page", defaultValue = "0", required = false) int page,
                                      @RequestParam(value = "size", defaultValue = "5", required = false) int size) {
        List<Tour> tours = tourService.findAll(page, size);
        return tours.stream()
                .map(dtoConverter::convertToDto)
                .peek(hateoasProvider::addLinks)
                .collect(Collectors.toList());
    }

    /**
     * Creates a new tour
     *
     * @param tourDto the tour to save
     * @return the created tour with HATEOAS
    */
    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public TourDto createTour(@RequestBody @Valid TourDto tourDto) {
        Tour createdTour = tourService.createTour(dtoConverter.convertToEntity(tourDto));
        TourDto dto = dtoConverter.convertToDto(createdTour);
        hateoasProvider.addLinks(dto);
        return dto;
    }

    /**
     * Updates a tour by id
     *
     * @param id the tour to update
     * @param tourDto the tour to update
     * @return the updated tour with HATEOAS
    */
    @PutMapping("/{id}")
    @ResponseStatus(HttpStatus.OK)
    public TourDto updateTour(@PathVariable Long id,
                                     @RequestBody @Valid TourDto tourDto) {
        Tour updatedTour = tourService.updateTour(id, dtoConverter.convertToEntity(tourDto));
        TourDto dto = dtoConverter.convertToDto(updatedTour);
        hateoasProvider.addLinks(dto);
        return dto;
    }

    /**
     * Deletes a tour by id
     *
     * @param id the tour id
     * @return the message on successful deletion
    */
    @DeleteMapping("/{id}")
    public ResponseEntity<String> deleteTour(@PathVariable Long id) {
        tourService.deleteTour(id);
        return ResponseEntity.status(HttpStatus.OK)
                .body("Tour with id " + id + " has been deleted");
    }
}