package com.company.explorecalifornia.hateaos.impl;

import com.company.explorecalifornia.hateaos.HateoasProvider;
import com.company.explorecalifornia.web.TourPackageController;
import com.company.explorecalifornia.web.dto.TourPackageDto;
import org.springframework.stereotype.Component;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;

/**
 * The class {@code TourPackageHateoasProvider} is an implementation of the interface {@link HateoasProvider}
 * for adding hyperlinks to {@link TourPackageDto} objects.
 *
 */
@Component
public class TourPackageHateoasProvider implements HateoasProvider<TourPackageDto> {

    private static final Class<TourPackageController> CONTROLLER = TourPackageController.class;

    @Override
    public void addLinks(TourPackageDto tourPackageDto) {
        tourPackageDto.add(linkTo(methodOn(CONTROLLER)
                .findById(tourPackageDto.getId())).withSelfRel());
        tourPackageDto.add(linkTo(methodOn(CONTROLLER)
                .findByCode(tourPackageDto.getCode())).withRel("get by code"));
        tourPackageDto.add(linkTo(methodOn(CONTROLLER)
                .findAllPackages(0, 1)).withRel("get all"));
        tourPackageDto.add(linkTo(methodOn(CONTROLLER)
                .createTourPackage(tourPackageDto)).withRel("create"));
        tourPackageDto.add(linkTo(methodOn(CONTROLLER)
                .updateTourPackage(tourPackageDto.getId(), tourPackageDto)).withRel("update"));
        tourPackageDto.add(linkTo(methodOn(CONTROLLER)
                .deleteTourPackage(tourPackageDto.getId())).withRel("delete"));
    }
}