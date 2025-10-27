package com.Movies.Services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.Movies.model.Movies;
import com.Movies.repo.MovieRepository;

@Service
@Transactional
public class MovieServices {
	@Autowired
	private MovieRepository movieRepo;
	public Movies create(Movies movies) {
		if(movies==null)
		{
			throw new RuntimeException("Invalid movies");
		}
		return movieRepo.save(movies);
	}
	public Movies read(long id) {
	    return movieRepo.findById(id)
	            .orElseThrow(() -> new RuntimeException("movies not found"));
	}
	public Movies update(Long id,Movies update) {
	    if (update == null|| id==null) {
	        throw new RuntimeException("Invalid movie");
	    }

	    if(movieRepo.existsById(id)) {
	    	Movies movies=movieRepo.getReferenceById(id);
	    }
	    else {
	    	throw new RuntimeException("Movie not found");
	    }
		return update;
	    	
	}
	public void delete(long id) {
	    Movies existing = movieRepo.findById(id)
	            .orElseThrow(() -> new RuntimeException("Movie not found"));

	    movieRepo.delete(existing);
	}

}
