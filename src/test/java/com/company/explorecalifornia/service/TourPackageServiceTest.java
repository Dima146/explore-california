package com.company.explorecalifornia.service;

import com.company.explorecalifornia.domain.TourPackage;
import com.company.explorecalifornia.repository.TourPackageRepository;
import com.company.explorecalifornia.service.impl.TourPackageServiceImpl;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import java.util.Optional;
import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class TourPackageServiceTest {

    @Mock
    private TourPackageRepository tourPackageRepository;
    @InjectMocks
    private TourPackageServiceImpl tourPackageService;
    private TourPackage tourPackage;

    @BeforeEach
    public void createEntity() {
        tourPackage = new TourPackage("CC", "California Calm");
        tourPackage.setId(1L);
    }

    @Test
    public void givenTourPackageId_whenFindTourPackageById_thenReturnTourPackage() {
        Long packageId = 1L;
        when(tourPackageRepository.findById(Mockito.anyLong())).thenReturn(Optional.of(tourPackage));
        TourPackage result = tourPackageService.findById(packageId);
        assertThat(result).isNotNull();
    }

    @Test
    public void givenTourPackage_whenSaveTourPackage_thenReturnSavedTourPackage() {
        when(tourPackageRepository.save(Mockito.any(TourPackage.class))).thenReturn(tourPackage);
        TourPackage savedPackage = tourPackageService.createTourPackage(tourPackage);
        assertThat(savedPackage).isNotNull();
    }

    @Test
    public void givenTourPackage_whenUpdateTourPackage_thenReturnUpdatedTourPackage() {
        Long packageId = 1L;
        when(tourPackageRepository.findById(Mockito.anyLong())).thenReturn(Optional.of(tourPackage));
        when(tourPackageRepository.save(Mockito.any(TourPackage.class))).thenReturn(tourPackage);

        tourPackage.setCode("CA");
        TourPackage updatedPackage = tourPackageService.updateTourPackage(packageId, tourPackage);

        assertThat(updatedPackage).isNotNull();
        assertThat(updatedPackage.getCode()).isEqualTo(tourPackage.getCode());
    }

    @Test
    public void givenTourPackageId_whenDeleteTourPackage_thenSuccessfullyDeleted() {
        when(tourPackageRepository.findById(tourPackage.getId())).thenReturn(Optional.of(tourPackage));
        tourPackageService.deleteTourPackage(tourPackage.getId());
        verify(tourPackageRepository, times(1)).deleteById(tourPackage.getId());
    }
}