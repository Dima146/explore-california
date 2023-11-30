package com.company.explorecalifornia.service;

import com.company.explorecalifornia.domain.Tour;
import com.company.explorecalifornia.domain.TourRating;
import com.company.explorecalifornia.domain.User;
import com.company.explorecalifornia.repository.TourRatingRepository;
import com.company.explorecalifornia.repository.TourRepository;
import com.company.explorecalifornia.repository.UserRepository;
import com.company.explorecalifornia.service.impl.TourRatingServiceImpl;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.data.domain.Pageable;
import java.util.List;
import java.util.Optional;
import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class TourRatingServiceTest {

    @Mock
    private TourRatingRepository tourRatingRepository;
    @Mock
    private TourRepository tourRepository;
    @Mock
    private UserRepository userRepository;
    @InjectMocks
    private TourRatingServiceImpl tourRatingService;

    private TourRating tourRating;
    private Tour tour;
    private User user;


    @BeforeEach
    public void createEntities() {
        tourRating = new TourRating();
        tourRating.setId(1L);
        tourRating.setComment("It was magnificent!");
        tourRating.setScore(5);

        tour = new Tour();
        tour.setId(1L);
        user = new User();
        user.setId(1L);
        tourRating.setTour(tour);
        tourRating.setUser(user);
    }

    @Test
    public void givenRatingId_whenFindRatingById_thenReturnRating() {
        when(tourRatingRepository.findById(Mockito.anyLong())).thenReturn(Optional.of(tourRating));
        TourRating result = tourRatingService.findById(tourRating.getId());
        assertThat(result).isNotNull();
    }

    @Test
    public void givenTourId_whenFindByTourId_thenReturnRating() {
        when(tourRatingRepository.findByTourId(Mockito.anyLong(), Mockito.any(Pageable.class)))
                .thenReturn(List.of(tourRating));

        List<TourRating> result = tourRatingService.findByTourId(tour.getId(), 1, 3);
        assertThat(result.isEmpty()).isFalse();
    }

    @Test
    public void givenTourRating_whenCreateRating_thenReturnCreatedRating() {
        when(tourRepository.findById(Mockito.anyLong())).thenReturn(Optional.of(tour));
        when(userRepository.findById(Mockito.anyLong())).thenReturn(Optional.of(user));
        when(tourRatingRepository.save(Mockito.any(TourRating.class))).thenReturn(tourRating);

        TourRating result = tourRatingService.createTourRating(tourRating);
        assertThat(result).isNotNull();
    }

    @Test
    public void givenTourRating_whenUpdateRating_thenReturnUpdatedRating() {
        when(tourRepository.findById(Mockito.anyLong())).thenReturn(Optional.of(tour));
        when(userRepository.findById(Mockito.anyLong())).thenReturn(Optional.of(user));
        when(tourRatingRepository.findById(Mockito.anyLong())).thenReturn(Optional.of(tourRating));
        when(tourRatingRepository.save(Mockito.any(TourRating.class))).thenReturn(tourRating);

        tourRating.setComment("It was magnificent and unforgettable!");
        TourRating result = tourRatingService.updateTourRating(tourRating.getId(), tourRating);
        assertThat(result).isNotNull();
        assertThat(result.getComment()).isEqualTo(tourRating.getComment());
    }

    @Test
    public void givenRatingId_whenDeleteRating_thenDeleted() {
        when(tourRatingRepository.findById(Mockito.anyLong())).thenReturn(Optional.of(tourRating));
        tourRatingService.deleteTourRating(tourRating.getId());
        verify(tourRatingRepository, times(1)).deleteById(tourRating.getId());
    }
}