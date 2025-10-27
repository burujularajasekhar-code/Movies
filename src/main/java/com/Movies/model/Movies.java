package com.Movies.model;
import java.util.ArrayList;
import java.util.List;
import jakarta.persistence.ElementCollection;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import lombok.Data;

@Entity
@Data
public class Movies {
	@Id
	@GeneratedValue
	private long id;
	private String name;
	private String directorName ;
	@ElementCollection
	private List<String> actors=new ArrayList<>();

}
