package com.company.explorecalifornia.web.dto;

import jakarta.validation.constraints.*;
import org.springframework.hateoas.RepresentationModel;

/**
 * Data Transfer Object for transferring tour rating attributes between the layers of the application
 *
 */
public class TourRatingDto extends RepresentationModel<TourRatingDto> {

    private Long id;

    @Min(value = 0, message = "The value of the score must be higher or equals to 0")
    @Max(value = 5, message = "The value of the score must be lower or equals to 5")
    private Integer score;

    @NotNull(message = "is required")
    @Size(max = 200, message = "must contain no more than 200 characters")
    private String comment;

    @NotNull(message = "is required")
    @Min(1)
    private Long tourId;

    @NotNull(message = "is required")
    @Min(1)
    private Long userId;

    public TourRatingDto() {
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Integer getScore() {
        return score;
    }

    public void setScore(Integer score) {
        this.score = score;
    }

    public String getComment() {
        return comment;
    }

    public void setComment(String comment) {
        this.comment = comment;
    }

    public Long getTourId() {
        return tourId;
    }

    public void setTourId(Long tourId) {
        this.tourId = tourId;
    }

    public Long getUserId() {
        return userId;
    }

    public void setUserId(Long userId) {
        this.userId = userId;
    }
}