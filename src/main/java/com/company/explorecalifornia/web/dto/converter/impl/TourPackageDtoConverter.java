package com.company.explorecalifornia.web.dto.converter.impl;

import com.company.explorecalifornia.domain.TourPackage;
import com.company.explorecalifornia.web.dto.TourPackageDto;
import com.company.explorecalifornia.web.dto.converter.DtoConverter;
import org.springframework.stereotype.Component;

@Component
public class TourPackageDtoConverter implements DtoConverter<TourPackage, TourPackageDto> {

    @Override
    public TourPackage convertToEntity(TourPackageDto dto) {
        TourPackage tourPackage = new TourPackage();
        tourPackage.setId(dto.getId());
        tourPackage.setName(dto.getName());
        tourPackage.setCode(dto.getCode());
        return tourPackage;
    }

    @Override
    public TourPackageDto convertToDto(TourPackage entity) {
        TourPackageDto tourPackageDto = new TourPackageDto();
        tourPackageDto.setId(entity.getId());
        tourPackageDto.setName(entity.getName());
        tourPackageDto.setCode(entity.getCode());
        return tourPackageDto;
    }
}