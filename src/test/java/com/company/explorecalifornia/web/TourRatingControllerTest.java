package com.company.explorecalifornia.web;

import com.company.explorecalifornia.Application;
import com.company.explorecalifornia.domain.TourRating;
import com.company.explorecalifornia.service.impl.TourRatingServiceImpl;
import com.company.explorecalifornia.web.dto.TourRatingDto;
import com.company.explorecalifornia.web.dto.converter.DtoConverter;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.hamcrest.Matchers;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import java.util.List;
import static org.hamcrest.Matchers.is;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;

@SpringBootTest(classes = Application.class)
@ExtendWith(MockitoExtension.class)
@AutoConfigureMockMvc(addFilters = false)
public class TourRatingControllerTest {

    @Autowired
    private MockMvc mockMvc;
    @Autowired
    private ObjectMapper objectMapper;
    @Autowired
    private DtoConverter<TourRating, TourRatingDto> dtoConverter;
    @MockBean
    private TourRatingServiceImpl tourRatingService;

    private TourRatingDto tourRatingDto;

    @BeforeEach
    public void createEntity() {
        tourRatingDto = new TourRatingDto();
        tourRatingDto.setId(1L);
        tourRatingDto.setScore(5);
        tourRatingDto.setComment("It was unforgettable!");
        tourRatingDto.setUserId(1L);
        tourRatingDto.setTourId(1L);
    }

    @Test
    public void givenTourRatingId_whenFindTourRatingById_thenReturnTourRating() throws Exception {
        when(tourRatingService.findById(Mockito.anyLong())).thenReturn(dtoConverter.convertToEntity(tourRatingDto));

        mockMvc.perform(get("/tour-ratings/{id}", 1))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.id", is(1)))
                .andExpect(jsonPath("$.score", is(5)))
                .andExpect(jsonPath("$.comment", is("It was unforgettable!")));
        verify(tourRatingService, times(1)).findById(tourRatingDto.getId());
    }

    @Test
    public void givenTourId_whenFindTourRatingByTourId_thenReturnTourRating() throws Exception {
        when(tourRatingService.findByTourId(Mockito.anyLong(), Mockito.anyInt(), Mockito.anyInt()))
                .thenReturn(List.of(dtoConverter.convertToEntity(tourRatingDto)));

        mockMvc.perform(get("/tour-ratings/tours/{tourId}", 1L)
                    .param("page", "1")
                    .param("size", "3"))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$", Matchers.hasSize(1)))
                .andExpect(jsonPath("$[0].tourId", is(1)));
        verify(tourRatingService, times(1)).findByTourId(tourRatingDto.getTourId(), 1, 3);
    }

    @Test
    public void givenTourRating_whenCreateTourRating_thenReturnCreatedRating() throws Exception {
        when(tourRatingService.createTourRating(Mockito.any(TourRating.class)))
                .thenReturn(dtoConverter.convertToEntity(tourRatingDto));

        mockMvc.perform(post("/tour-ratings")
                .content(objectMapper.writeValueAsString(tourRatingDto))
                .header(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_JSON))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.id", is(1)))
                .andExpect(jsonPath("$.score", is(5)));
        verify(tourRatingService, times(1))
                .createTourRating(dtoConverter.convertToEntity(tourRatingDto));
    }

    @Test
    public void givenTourRating_whenUpdateTourRating_thenReturnUpdatedRating() throws Exception {
        when(tourRatingService.findById(Mockito.anyLong())).thenReturn(dtoConverter.convertToEntity(tourRatingDto));
        tourRatingDto.setScore(4);
        when(tourRatingService.updateTourRating(Mockito.anyLong(), Mockito.any(TourRating.class)))
                .thenReturn(dtoConverter.convertToEntity(tourRatingDto));

        mockMvc.perform(put("/tour-ratings/{id}", 1)
                        .content(objectMapper.writeValueAsString(tourRatingDto))
                        .header(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id", is(1)))
                .andExpect(jsonPath("$.score", is(4)));
        verify(tourRatingService, times(1))
                .updateTourRating(1L, dtoConverter.convertToEntity(tourRatingDto));
    }

    @Test
    public void givenTourRatingId_whenDeleteTourRating_thenDeleteSuccessfully() throws Exception {
        mockMvc.perform(delete("/tour-ratings/{id}", 1))
                .andExpect(status().isOk());
        verify(tourRatingService, times(1)).deleteTourRating(tourRatingDto.getId());
    }
}
