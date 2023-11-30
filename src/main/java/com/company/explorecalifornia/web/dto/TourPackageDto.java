package com.company.explorecalifornia.web.dto;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import org.springframework.hateoas.RepresentationModel;

/**
 * Data Transfer Object for transferring tour package attributes between the layers of the application
 *
 */
public class TourPackageDto extends RepresentationModel<TourPackageDto> {

    private Long id;

    @NotNull(message = "is required")
    @Size(min = 2, max = 4, message = "must contain between 2 and 4 characters")
    private String code;

    @NotNull(message = "is required")
    @Size(min = 2, max = 50, message = "must contain between 2 and 50 characters")
    private String name;

    public TourPackageDto() {
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}