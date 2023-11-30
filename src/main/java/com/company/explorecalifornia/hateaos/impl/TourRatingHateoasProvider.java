package com.company.explorecalifornia.hateaos.impl;

import com.company.explorecalifornia.hateaos.HateoasProvider;
import com.company.explorecalifornia.web.TourRatingController;
import com.company.explorecalifornia.web.dto.TourRatingDto;
import org.springframework.stereotype.Component;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;

/**
 * The class {@code TourRatingHateoasProvider} is an implementation of the interface {@link HateoasProvider}
 * for adding hyperlinks to {@link TourRatingDto} objects.
 *
 */
@Component
public class TourRatingHateoasProvider implements HateoasProvider<TourRatingDto> {

    private static final Class<TourRatingController> CONTROLLER = TourRatingController.class;

    @Override
    public void addLinks(TourRatingDto tourRatingDto) {
        tourRatingDto.add(linkTo(methodOn(CONTROLLER)
                .findById(tourRatingDto.getId())).withSelfRel());
        tourRatingDto.add(linkTo(methodOn(CONTROLLER)
                .findByTourId(tourRatingDto.getTourId(), 0, 1)).withRel("get by tour id"));
        tourRatingDto.add(linkTo(methodOn(CONTROLLER)
                .findByUserId(tourRatingDto.getUserId(), 0, 1)).withRel("get by user id"));
        tourRatingDto.add(linkTo(methodOn(CONTROLLER)
                .getAverageScore(tourRatingDto.getTourId())).withRel("get average score"));
        tourRatingDto.add(linkTo(methodOn(CONTROLLER)
                .createTourRating(tourRatingDto)).withRel("create"));
        tourRatingDto.add(linkTo(methodOn(CONTROLLER)
                .updateTourRating(tourRatingDto.getId(), tourRatingDto)).withRel("update"));
        tourRatingDto.add(linkTo(methodOn(CONTROLLER)
                .deleteTourPackage(tourRatingDto.getId())).withRel("delete"));
    }
}