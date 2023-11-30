package com.company.explorecalifornia.hateaos.impl;

import com.company.explorecalifornia.domain.Difficulty;
import com.company.explorecalifornia.hateaos.HateoasProvider;
import com.company.explorecalifornia.web.TourController;
import com.company.explorecalifornia.web.dto.TourDto;
import org.springframework.stereotype.Component;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;

/**
 * The class {@code TourHateoasProvider} is an implementation of the interface {@link HateoasProvider}
 * for adding hyperlinks to {@link TourDto} objects.
 *
 */
@Component
public class TourHateoasProvider implements HateoasProvider<TourDto> {

    private static final Class<TourController> CONTROLLER = TourController.class;

    @Override
    public void addLinks(TourDto tourDto) {
        tourDto.add(linkTo(methodOn(CONTROLLER)
                .findById(tourDto.getId())).withSelfRel());
        tourDto.add(linkTo(methodOn(CONTROLLER)
                .findByDifficulty(
                        Difficulty.valueOf(tourDto.getDifficulty()), 0, 1)).withRel("get by difficulty"));
        tourDto.add(linkTo(methodOn(CONTROLLER)
                .findByPackageCode(tourDto.getTourPackageCode(), 0, 1)).withRel("get by package code"));
        tourDto.add(linkTo(methodOn(CONTROLLER)
                .findAllTours(0, 1)).withRel("get all"));
        tourDto.add(linkTo(methodOn(CONTROLLER)
                .createTour(tourDto)).withRel("create"));
        tourDto.add(linkTo(methodOn(CONTROLLER)
                .updateTour(tourDto.getId(), tourDto)).withRel("update"));
        tourDto.add(linkTo(methodOn(CONTROLLER)
                .deleteTour(tourDto.getId())).withRel("delete"));
    }
}