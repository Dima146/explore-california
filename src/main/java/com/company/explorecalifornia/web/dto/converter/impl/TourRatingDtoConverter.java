package com.company.explorecalifornia.web.dto.converter.impl;

import com.company.explorecalifornia.domain.Tour;
import com.company.explorecalifornia.domain.TourRating;
import com.company.explorecalifornia.domain.User;
import com.company.explorecalifornia.web.dto.TourRatingDto;
import com.company.explorecalifornia.web.dto.converter.DtoConverter;
import org.springframework.stereotype.Component;

@Component
public class TourRatingDtoConverter implements DtoConverter<TourRating, TourRatingDto> {

    @Override
    public TourRating convertToEntity(TourRatingDto dto) {
        TourRating tourRating = new TourRating();
        tourRating.setId(dto.getId());
        tourRating.setScore(dto.getScore());
        tourRating.setComment(dto.getComment());

        Tour tour = new Tour();
        tour.setId(dto.getTourId());
        tourRating.setTour(tour);

        User user = new User();
        user.setId(dto.getUserId());
        tourRating.setUser(user);
        return tourRating;
    }

    @Override
    public TourRatingDto convertToDto(TourRating entity) {
        TourRatingDto tourRatingDto = new TourRatingDto();
        tourRatingDto.setId(entity.getId());
        tourRatingDto.setScore(entity.getScore());
        tourRatingDto.setComment(entity.getComment());
        tourRatingDto.setTourId(entity.getTour().getId());
        tourRatingDto.setUserId(entity.getUser().getId());
        return tourRatingDto;
    }
}