package com.Movies.Services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.Exceptions.InvalidDataException;
import com.Exceptions.NotFoundException;
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
			throw new InvalidDataException("Invalid movies:null");
		}
		return movieRepo.save(movies);
	}
	public Movies read(long id) {
	    return movieRepo.findById(id)
	            .orElseThrow(() -> new NotFoundException("movies not found with id :"+id));
	}
	public Movies update(Long id,Movies update) {
	    if (update == null|| id==null) {
	    	throw new InvalidDataException("Invalid movies:null");
	    }

	    if(movieRepo.existsById(id)) {
	    	Movies movies=movieRepo.getReferenceById(id);
	    }
	    else {
	    	throw new NotFoundException("movies not found with id :"+id);
	    }
		return update;
	    	
	}
	public void delete(long id) {
	    Movies existing = movieRepo.findById(id)
	            .orElseThrow(() -> new NotFoundException("movies not found with id :"+id));

	    movieRepo.delete(existing);
	}

}
