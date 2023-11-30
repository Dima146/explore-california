package com.company.explorecalifornia.web;

import com.company.explorecalifornia.Application;
import com.company.explorecalifornia.domain.Difficulty;
import com.company.explorecalifornia.domain.Tour;
import com.company.explorecalifornia.service.impl.TourServiceImpl;
import com.company.explorecalifornia.web.dto.TourDto;
import com.company.explorecalifornia.web.dto.converter.DtoConverter;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.hamcrest.Matchers;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentMatchers;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import static org.hamcrest.Matchers.is;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import java.math.BigDecimal;
import java.util.List;

@SpringBootTest(classes = Application.class)
@ExtendWith(MockitoExtension.class)
@AutoConfigureMockMvc(addFilters = false)
public class TourControllerTest {

    @Autowired
    private MockMvc mockMvc;
    @Autowired
    private ObjectMapper objectMapper;
    @Autowired
    private DtoConverter<Tour, TourDto> dtoConverter;
    @MockBean
    private TourServiceImpl tourService;

    private TourDto tourDto;

    @BeforeEach
    public void createEntities() {
        tourDto = new TourDto();
        tourDto.setId(1L);
        tourDto.setTitle("Big Sur Retreat");
        tourDto.setDescription("The region know as Big Sur is...");
        tourDto.setKeywords("Hiking, National Parks, Big Sur");
        tourDto.setPrice(BigDecimal.valueOf(750.00));
        tourDto.setDuration("3 days");
        tourDto.setDifficulty(String.valueOf(Difficulty.MEDIUM));
        tourDto.setRegion("Central Coast");
        tourDto.setTourPackageCode("CC");
    }

    @Test
    public void givenTourId_whenFindTourById_thenReturnTour() throws Exception {
        when(tourService.findById(Mockito.anyLong())).thenReturn(dtoConverter.convertToEntity(tourDto));

        mockMvc.perform(get("/tours/{id}", 1))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.id", is(1)))
                .andExpect(jsonPath("$.title", is("Big Sur Retreat")));
        verify(tourService, times(1)).findById(tourDto.getId());
    }

    @Test
    public void givenTourDifficulty_whenFindTourByDifficulty_thenReturnTour() throws Exception {
        Difficulty difficulty = Difficulty.MEDIUM;
        when(tourService.findByDifficulty(Mockito.any(Difficulty.class), Mockito.anyInt(), Mockito.anyInt()))
                .thenReturn(List.of(dtoConverter.convertToEntity(tourDto)));

        mockMvc.perform(get("/tours")
                        .param("difficulty", "MEDIUM")
                        .param("page", "1")
                        .param("size", "3"))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$", Matchers.hasSize(1)));
        verify(tourService, times(1)).findByDifficulty(difficulty, 1, 3);
    }

    @Test
    public void givenTour_whenCreateTour_thenReturnCreatedTour() throws Exception {
        when(tourService.createTour(Mockito.any(Tour.class))).thenReturn(dtoConverter.convertToEntity(tourDto));

        mockMvc.perform(post("/tours")
                        .content(objectMapper.writeValueAsString(tourDto))
                        .header(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_JSON))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.id", is(1)))
                .andExpect(jsonPath("$.title", is("Big Sur Retreat")));
        verify(tourService, times(1)).createTour(
                ArgumentMatchers.refEq(dtoConverter.convertToEntity(tourDto)));
    }

    @Test
    public void givenTour_whenUpdateTour_thenReturnUpdatedTour() throws Exception {
        when(tourService.findById(Mockito.anyLong())).thenReturn(dtoConverter.convertToEntity(tourDto));
        tourDto.setDuration("4 days");
        when(tourService.updateTour(Mockito.any(Long.class), Mockito.any(Tour.class)))
                .thenReturn(dtoConverter.convertToEntity(tourDto));

        mockMvc.perform(put("/tours/{id}", 1)
                        .content(objectMapper.writeValueAsString(tourDto))
                        .header(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id", is(1)))
                .andExpect(jsonPath("$.title", is("Big Sur Retreat")))
                .andExpect(jsonPath("$.duration", is("4 days")));
        verify(tourService, times(1)).updateTour(
                ArgumentMatchers.refEq(tourDto.getId()), ArgumentMatchers.refEq(dtoConverter.convertToEntity(tourDto)));
    }

    @Test
    public void givenTourId_whenDeleteTourById_SuccessfullyDeleted() throws Exception {
        mockMvc.perform(delete("/tours/{id}", 1))
                .andExpect(status().isOk());
        verify(tourService, times(1)).deleteTour(tourDto.getId());
    }
}