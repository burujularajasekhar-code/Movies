package com.Movies.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.Movies.Services.MovieServices;
import com.Movies.model.Movies;

import lombok.extern.slf4j.Slf4j;

@RestController
@RequestMapping("/movies")
@Slf4j
public class MoviesController {
	@Autowired
	private MovieServices moviesServ;
	@GetMapping("/{id}")
	public ResponseEntity<Movies> getMovies(@PathVariable long id){
	    Movies movie = moviesServ.read(id);
	    if (movie == null) {
	        return ResponseEntity.notFound().build();
	    }
	    log.info("Returned movie with id:{}",id);
	    return ResponseEntity.ok(movie);
	}
	
	@PostMapping
	public ResponseEntity<Movies> createMovies(@RequestBody Movies movies){
	    Movies savedMovie = moviesServ.create(movies);
	    log.info("movie with id:{}",movies);
	    return ResponseEntity.status(HttpStatus.CREATED).body(savedMovie);
	}
	@PutMapping("/{id}")
	public ResponseEntity<Movies> updateMovies(@PathVariable long id, @RequestBody Movies movie) {
	    Movies updatedMovie = moviesServ.update(id, movie);
	    return ResponseEntity.ok(updatedMovie);
	}
	@DeleteMapping("/{id}")
	public ResponseEntity<Void> deleteMovies(@PathVariable long id) {
	    Movies existingMovie = moviesServ.read(id);
	    if (existingMovie == null) {
	        return ResponseEntity.notFound().build();
	    }

	    moviesServ.delete(id);
	    return ResponseEntity.noContent().build(); // 204 No Content
	}
}
