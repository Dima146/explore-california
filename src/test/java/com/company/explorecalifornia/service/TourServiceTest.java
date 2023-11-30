package com.company.explorecalifornia.service;

import com.company.explorecalifornia.domain.Difficulty;
import com.company.explorecalifornia.domain.Region;
import com.company.explorecalifornia.domain.Tour;
import com.company.explorecalifornia.domain.TourPackage;
import com.company.explorecalifornia.repository.RegionRepository;
import com.company.explorecalifornia.repository.TourPackageRepository;
import com.company.explorecalifornia.repository.TourRepository;
import com.company.explorecalifornia.service.impl.TourServiceImpl;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.data.domain.Pageable;
import java.math.BigDecimal;
import java.util.List;
import java.util.Optional;
import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class TourServiceTest {

    @Mock
    private TourRepository tourRepository;
    @Mock
    private RegionRepository regionRepository;
    @Mock
    private TourPackageRepository tourPackageRepository;
    @InjectMocks
    private TourServiceImpl tourService;

    private Tour tour;
    private TourPackage tourPackage;

    @BeforeEach
    public void createEntities() {
        tour = new Tour();
        tour.setId(1L);
        tour.setTitle("Big Sur Retreat");
        tour.setDescription("The region know as Big Sur is...");
        tour.setKeywords("Hiking, National Parks, Big Sur");
        tour.setPrice(BigDecimal.valueOf(750.00));
        tour.setDuration("3 days");
        tour.setDifficulty(Difficulty.MEDIUM);

        Region region = new Region("Central Coast");
        tour.setRegion(region);

        tourPackage = new TourPackage("CC", "California Calm");
        tour.setTourPackage(tourPackage);
    }

    @Test
    public void givenTourId_whenFindTourById_thenReturnTour() {
        when(tourRepository.findById(Mockito.anyLong())).thenReturn(Optional.of(tour));
        Tour result = tourService.findById(tour.getId());
        assertThat(result).isNotNull();
    }

    @Test
    public void givenTourDifficulty_whenFindTourByDifficulty_ReturnList() {
        Pageable pageable = PageableCreator.createPageRequest(1, 3);
        when(tourRepository.findByDifficulty(Difficulty.valueOf("MEDIUM"), pageable)).thenReturn(List.of(tour));

        List<Tour> result = tourService.findByDifficulty(Difficulty.valueOf("MEDIUM"), 1, 3);
        assertThat(result.size()).isEqualTo(1);
    }

    @Test
    public void givenTour_whenSaveTour_thenReturnSavedTour() {
        when(tourRepository.save(Mockito.any(Tour.class))).thenReturn(tour);
        when(tourPackageRepository.findByCode(Mockito.anyString())).thenReturn(Optional.of(tourPackage));
        Tour savedTour = tourService.createTour(tour);
        assertThat(savedTour).isNotNull();
    }

    @Test
    public void givenTour_whenUpdateTour_thenReturnUpdatedTour() {
        when(tourRepository.findById(Mockito.anyLong())).thenReturn(Optional.of(tour));
        when(tourRepository.save(Mockito.any(Tour.class))).thenReturn(tour);
        when(tourPackageRepository.findByCode(Mockito.anyString())).thenReturn(Optional.of(tourPackage));

        tour.setPrice(BigDecimal.valueOf(700.00));
        Tour updatedTour = tourService.updateTour(tour.getId(), tour);
        assertThat(updatedTour).isNotNull();
        assertThat(updatedTour.getPrice()).isEqualTo(tour.getPrice());
    }

    @Test
    public void givenTourId_whenDeleteTour_thenSuccessfullyDeleted() {
        when(tourRepository.findById(Mockito.anyLong())).thenReturn(Optional.of(tour));
        tourService.deleteTour(tour.getId());
        verify(tourRepository, times(1)).deleteById(tour.getId());
    }
}