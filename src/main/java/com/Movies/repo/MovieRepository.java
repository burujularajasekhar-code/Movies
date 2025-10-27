package com.Movies.repo;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import com.Movies.model.Movies;
@Repository
public interface MovieRepository extends JpaRepository<Movies, Long>{

}
