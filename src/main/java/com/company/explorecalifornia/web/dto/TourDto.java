package com.company.explorecalifornia.web.dto;

import com.company.explorecalifornia.domain.Difficulty;
import com.company.explorecalifornia.service.validation.ValueOfEnum;
import jakarta.validation.constraints.DecimalMax;
import jakarta.validation.constraints.DecimalMin;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import org.springframework.hateoas.RepresentationModel;
import java.math.BigDecimal;

/**
 * Data Transfer Object for transferring tour attributes between the layers of the application.
 *
 */
public class TourDto extends RepresentationModel<TourDto> {

    private Long id;

    @NotNull(message = "is required")
    @Size(min = 4, max = 100, message = "must contain between 4 and 100 characters")
    private String title;

    @NotNull(message = "is required")
    @Size(min = 10, max = 2000, message = "must contain between 10 and 2000 characters")
    private String description;

    @NotNull(message = "is required")
    @Size(min = 3, max = 100, message = "must contain between 10 and 100 characters")
    private String keywords;

    @DecimalMin(value = "1.0", message = "must be greater than or equal to 0")
    @DecimalMax(value = "5000.0", message = "must be lower than or equal to 5000")
    private BigDecimal price;

    @NotNull(message = "is required")
    @Size(min = 3, max = 32, message = "must contain between 3 and 32 characters")
    private String duration;

    @NotNull(message = "is required")
    @ValueOfEnum(enumClass = Difficulty.class)
    private String difficulty;

    @NotNull(message = "is required")
    @Size(min = 3, max = 15, message = "must contain between 3 and 15 characters")
    private String region;

    @NotNull(message = "is required")
    @Size(min = 2, max = 4, message = "must contain between 2 and 4 characters")
    private String tourPackageCode;

    public TourDto() {
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getKeywords() {
        return keywords;
    }

    public void setKeywords(String keywords) {
        this.keywords = keywords;
    }

    public BigDecimal getPrice() {
        return price;
    }

    public void setPrice(BigDecimal price) {
        this.price = price;
    }

    public String getDuration() {
        return duration;
    }

    public void setDuration(String duration) {
        this.duration = duration;
    }

    public String getDifficulty() {
        return difficulty;
    }

    public void setDifficulty(String difficulty) {
        this.difficulty = difficulty;
    }

    public String getRegion() {
        return region;
    }

    public void setRegion(String region) {
        this.region = region;
    }

    public String getTourPackageCode() {
        return tourPackageCode;
    }

    public void setTourPackageCode(String tourPackageCode) {
        this.tourPackageCode = tourPackageCode;
    }
}