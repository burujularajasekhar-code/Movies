package com.Movies.controllerTest;

import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.CoreMatchers.notNullValue;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import java.util.List;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.ResultActions;

import com.Movies.controller.MoviesController;
import com.Movies.model.Movies;
import com.Movies.repo.MovieRepository;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.Movies.MoviesApplication;
import com.Movies.Services.MovieServices;


@WebMvcTest(MoviesController.class)
@SpringBootTest
@ContextConfiguration(classes = MoviesApplication.class)
public class MoviesControllerTest {

    @Autowired
    private MockMvc mockMvc;
    @Autowired
    private ObjectMapper objectMapper;
    @MockBean
    private MovieServices moviesServ;
    @Autowired
    private MovieRepository movieRepo;
    @BeforeEach

    void cleanup() {

    	movieRepo.deleteAllInBatch();

    }
    @Test
    void givenMovie_whenCreateMovie_thenReturnSavedMovie() throws Exception {
        // Given
        Movies movie = new Movies();
        movie.setName("RRR");
        movie.setDirectorName("SS Rajamouli");
        movie.setActors(List.of("NTR", "Ram Charan", "Alia Bhatt"));

        // When
        ResultActions response = mockMvc.perform(post("/movies")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(movie)));

        // Then
        response.andDo(print())
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id", is(notNullValue())))
                .andExpect(jsonPath("$.name", is(movie.getName())))
                .andExpect(jsonPath("$.director", is(movie.getDirectorName())))
                .andExpect(jsonPath("$.actors", is(movie.getActors())));
    }
    @Test
    void givenMovieId_whenFetchMovie_thenReturnMovie() throws Exception {
        // Given
        Movies movie = new Movies();
        movie.setName("RRR");
        movie.setDirectorName("SS Rajamouli");
        movie.setActors(List.of("NTR", "Ram Charan", "Alia Bhatt"));

        Movies savedMovie = movieRepo.save(movie);
        Long id = savedMovie.getId();

        // When
        ResultActions response = mockMvc.perform(get("/movies/" + id)
                .contentType(MediaType.APPLICATION_JSON));

        // Then
        response.andDo(print())
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id", is(id.intValue())))
                .andExpect(jsonPath("$.name", is(movie.getName())))
                .andExpect(jsonPath("$.director", is(movie.getDirectorName())))
                .andExpect(jsonPath("$.actors", is(movie.getActors())));
    }
    @Test
    void givenSavedMovie_WhenUpdateMovie_thenMovieUpdatedInDb() throws Exception {
        // Given
        Movies movie = new Movies();
        movie.setName("RRR");
        movie.setDirectorName("SS Rajamouli");
        movie.setActors(List.of("NTR", "Ram Charan", "Alia Bhatt"));

        Movies savedMovie = movieRepo.save(movie);
        Long id = savedMovie.getId();

        // Update movie
        movie.setActors(List.of("NTR", "Ram Charan", "Alia Bhatt", "Ajay Devgan"));

        // When
        ResultActions response = mockMvc.perform(put("/movies/" + id)
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(movie)));

        // Then
        response.andDo(print())
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(id.intValue()))
                .andExpect(jsonPath("$.name").value(movie.getName()))
                .andExpect(jsonPath("$.director").value(movie.getDirectorName()))
                .andExpect(jsonPath("$.actors[3]").value("Ajay Devgan"));
    }
    @Test
    void givenMovie_whenDeleteRequest_thenMovieRemovedFromDb() throws Exception {
        // Given
        Movies movie = new Movies();
        movie.setName("RRR");
        movie.setDirectorName("SS Rajamouli");
        movie.setActors(List.of("NTR", "Ram Charan", "Alia Bhatt"));

        Movies savedMovie = movieRepo.save(movie);
        Long id = savedMovie.getId();

        // When
        mockMvc.perform(delete("/movies/" + id)
                .contentType(MediaType.APPLICATION_JSON))
            .andDo(print())
            .andExpect(status().isOk());

        // Then
        assertFalse(movieRepo.findById(id).isPresent());
    }

}