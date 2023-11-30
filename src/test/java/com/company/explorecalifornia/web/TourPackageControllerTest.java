package com.company.explorecalifornia.web;

import com.company.explorecalifornia.Application;
import com.company.explorecalifornia.domain.TourPackage;
import com.company.explorecalifornia.service.impl.TourPackageServiceImpl;
import com.company.explorecalifornia.web.dto.TourPackageDto;
import com.company.explorecalifornia.web.dto.converter.DtoConverter;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import static org.hamcrest.Matchers.is;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;

@SpringBootTest(classes = Application.class)
@ExtendWith(MockitoExtension.class)
@AutoConfigureMockMvc(addFilters = false)
public class TourPackageControllerTest {

    @Autowired
    private MockMvc mockMvc;
    @Autowired
    private ObjectMapper objectMapper;
    @Autowired
    private DtoConverter<TourPackage, TourPackageDto> dtoConverter;
    @MockBean
    private TourPackageServiceImpl tourPackageService;

    private TourPackageDto tourPackageDto;

    @BeforeEach
    public void createEntity() {
        tourPackageDto = new TourPackageDto();
        tourPackageDto.setId(1L);
        tourPackageDto.setCode("CC");
        tourPackageDto.setName("California Calm");
    }

    @Test
    public void givenTourPackageId_whenFindPackageById_ReturnPackage() throws Exception {
        when(tourPackageService.findById(Mockito.anyLong()))
                .thenReturn(dtoConverter.convertToEntity(tourPackageDto));

        mockMvc.perform(get("/tour-packages/{id}", 1))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.id", is(1)))
                .andExpect(jsonPath("$.code", is("CC")))
                .andExpect(jsonPath("$.name", is("California Calm")));
        verify(tourPackageService, times(1)).findById(tourPackageDto.getId());
    }

    @Test
    public void givenTourPackage_whenCreateTourPackage_thenReturnCreatedPackage() throws Exception {
        when(tourPackageService.createTourPackage(Mockito.any(TourPackage.class)))
                .thenReturn(dtoConverter.convertToEntity(tourPackageDto));

        mockMvc.perform(post("/tour-packages")
                .content(objectMapper.writeValueAsString(tourPackageDto))
                .header(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_JSON))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.id", is(1)))
                .andExpect(jsonPath("$.code", is("CC")))
                .andExpect(jsonPath("$.name", is("California Calm")));
        verify(tourPackageService, times(1))
                .createTourPackage(dtoConverter.convertToEntity(tourPackageDto));
    }

    @Test
    public void givenTourPackage_whenUpdateTourPackage_thenReturnUpdatedPackage() throws Exception {
        when(tourPackageService.findById(Mockito.anyLong())).thenReturn(dtoConverter.convertToEntity(tourPackageDto));
        tourPackageDto.setCode("CA");
        when(tourPackageService.updateTourPackage(Mockito.any(Long.class), Mockito.any(TourPackage.class)))
                .thenReturn(dtoConverter.convertToEntity(tourPackageDto));

        mockMvc.perform(put("/tour-packages/{id}", 1)
                        .content(objectMapper.writeValueAsString(tourPackageDto))
                        .header(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.id", is(1)))
                .andExpect(jsonPath("$.code", is("CA")))
                .andExpect(jsonPath("$.name", is("California Calm")));
        verify(tourPackageService, times(1))
                .updateTourPackage(1L, dtoConverter.convertToEntity(tourPackageDto));
    }

    @Test
    public void givenTourPackageId_whenDeletePackage_thenDeletedSuccessfully() throws Exception {
        mockMvc.perform(delete("/tour-packages/{id}", 1))
                .andExpect(status().isOk());
        verify(tourPackageService, times(1)).deleteTourPackage(tourPackageDto.getId());
    }
}