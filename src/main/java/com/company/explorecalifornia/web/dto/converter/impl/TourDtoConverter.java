package com.company.explorecalifornia.web.dto.converter.impl;

import com.company.explorecalifornia.domain.Difficulty;
import com.company.explorecalifornia.domain.Region;
import com.company.explorecalifornia.domain.Tour;
import com.company.explorecalifornia.domain.TourPackage;
import com.company.explorecalifornia.web.dto.TourDto;
import com.company.explorecalifornia.web.dto.converter.DtoConverter;
import org.springframework.stereotype.Component;

@Component
public class TourDtoConverter implements DtoConverter<Tour, TourDto> {

    @Override
    public Tour convertToEntity(TourDto dto) {
        Tour tour = new Tour();
        tour.setId(dto.getId());
        tour.setTitle(dto.getTitle());
        tour.setDescription(dto.getDescription());
        tour.setKeywords(dto.getKeywords());
        tour.setPrice(dto.getPrice());
        tour.setDuration(dto.getDuration());
        tour.setDifficulty(Difficulty.valueOf(dto.getDifficulty()));

        Region region = new Region();
        region.setName(dto.getRegion());
        tour.setRegion(region);

        TourPackage tourPackage = new TourPackage();
        tourPackage.setCode(dto.getTourPackageCode());
        tour.setTourPackage(tourPackage);
        return tour;
    }

    @Override
    public TourDto convertToDto(Tour entity) {
        TourDto tourDto = new TourDto();
        tourDto.setId(entity.getId());
        tourDto.setTitle(entity.getTitle());
        tourDto.setDescription(entity.getDescription());
        tourDto.setKeywords(entity.getKeywords());
        tourDto.setPrice(entity.getPrice());
        tourDto.setDuration(entity.getDuration());
        tourDto.setDifficulty(String.valueOf(entity.getDifficulty()));
        tourDto.setRegion(entity.getRegion().getName());
        tourDto.setTourPackageCode(entity.getTourPackage().getCode());
        return tourDto;
    }
}