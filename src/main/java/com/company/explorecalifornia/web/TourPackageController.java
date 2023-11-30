package com.company.explorecalifornia.web;

import com.company.explorecalifornia.domain.TourPackage;
import com.company.explorecalifornia.hateaos.HateoasProvider;
import com.company.explorecalifornia.service.TourPackageService;
import com.company.explorecalifornia.web.dto.TourPackageDto;
import com.company.explorecalifornia.web.dto.converter.DtoConverter;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.List;
import java.util.stream.Collectors;

/**
 * The class {@code TourPackageController} is a controller which contains REST endpoints
 * for performing operations on tour packages.
 *
 */
@RestController
@RequestMapping("/tour-packages")
public class TourPackageController {

    private final TourPackageService tourPackageService;
    private final DtoConverter<TourPackage, TourPackageDto> dtoConverter;
    private final HateoasProvider<TourPackageDto> hateoasProvider;

    @Autowired
    public TourPackageController(TourPackageService tourPackageService,
                                 DtoConverter<TourPackage, TourPackageDto> dtoConverter,
                                 HateoasProvider<TourPackageDto> hateoasProvider) {
        this.tourPackageService = tourPackageService;
        this.dtoConverter = dtoConverter;
        this.hateoasProvider = hateoasProvider;
    }

    /**
     * Gets a tour package by id
     *
     * @param id - the tour package id
     * @return the tour package with HATEOAS
    */
    @GetMapping("/{id}")
    @ResponseStatus(HttpStatus.OK)
    public TourPackageDto findById(@PathVariable Long id) {
        TourPackage tourPackage = tourPackageService.findById(id);
        TourPackageDto tourPackageDto = dtoConverter.convertToDto(tourPackage);
        hateoasProvider.addLinks(tourPackageDto);
        return tourPackageDto;
    }

    /**
     * Gets a tour package by code
     *
     * @param code - the tour package code
     * @return the tour package with HATEOAS
    */
    @GetMapping(params = {"code"})
    @ResponseStatus(HttpStatus.OK)
    public TourPackageDto findByCode(@RequestParam String code) {
        TourPackage tourPackage = tourPackageService.findByCode(code);
        TourPackageDto tourPackageDto = dtoConverter.convertToDto(tourPackage);
        hateoasProvider.addLinks(tourPackageDto);
        return tourPackageDto;
    }

    /**
     * Gets all tour packages
     *
     * @param page - the page number
     * @param size - the size of the page
     * @return the list of tour packages with HATEOAS
    */
    @GetMapping
    @ResponseStatus(HttpStatus.OK)
    public List<TourPackageDto> findAllPackages(
                                     @RequestParam(value = "page", defaultValue = "0", required = false) int page,
                                     @RequestParam(value = "size", defaultValue = "5", required = false) int size) {
        List<TourPackage> tourPackages = tourPackageService.findAll(page, size);
        return tourPackages.stream()
                .map(dtoConverter::convertToDto)
                .peek(hateoasProvider::addLinks)
                .collect(Collectors.toList());
    }

    /**
     * Creates a new tour package
     *
     * @param tourPackageDto - the tour package to create
     * @return the tour package with HATEOAS
    */
    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public TourPackageDto createTourPackage(@RequestBody @Valid TourPackageDto tourPackageDto) {
        TourPackage createdTourPackage =
                tourPackageService.createTourPackage(dtoConverter.convertToEntity(tourPackageDto));
        TourPackageDto packageDto = dtoConverter.convertToDto(createdTourPackage);
        hateoasProvider.addLinks(packageDto);
        return packageDto;
    }

    /**
     * Updates a tour package by id
     *
     * @param id - the tour package id
     * @param tourPackageDto - the tour package to update
     * @return the tour package with HATEOAS
    */
    @PutMapping("/{id}")
    @ResponseStatus(HttpStatus.OK)
    public TourPackageDto updateTourPackage(@PathVariable Long id,
                                         @RequestBody @Valid TourPackageDto tourPackageDto) {
        TourPackage updatedTourPackage =
                tourPackageService.updateTourPackage(id, dtoConverter.convertToEntity(tourPackageDto));
        TourPackageDto packageDto = dtoConverter.convertToDto(updatedTourPackage);
        hateoasProvider.addLinks(packageDto);
        return packageDto;
    }

    /**
     * Deletes a tour package by id
     *
     * @param id - the tour package id
     * @return the message on successful deletion
    */
    @DeleteMapping("/{id}")
    public ResponseEntity<String> deleteTourPackage(@PathVariable Long id) {
        tourPackageService.deleteTourPackage(id);
        return ResponseEntity.status(HttpStatus.OK)
                .body("Tour package with id " + id + " has been deleted");
    }
}