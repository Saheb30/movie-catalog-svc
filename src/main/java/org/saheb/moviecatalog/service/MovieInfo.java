package org.saheb.moviecatalog.service;

import org.saheb.moviecatalog.model.CatalogItem;
import org.saheb.moviecatalog.model.Movie;
import org.saheb.moviecatalog.model.Rating;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import com.netflix.hystrix.contrib.javanica.annotation.HystrixCommand;

@Service
public class MovieInfo {
	@Autowired
	private RestTemplate restTemplate;

	@HystrixCommand(fallbackMethod = "getMovieInfoFallback")
	public CatalogItem getMovieInfo(Rating rating) {
		Movie movie = restTemplate.getForObject("http://movie-info-service/movies/" + rating.getMovieId(), Movie.class);
		return new CatalogItem(movie.getName(), movie.getDescription(), rating.getRating());
	}

	public CatalogItem getMovieInfoFallback(Rating rating) {
		return new CatalogItem("Dafault", "Movie service not available", 0);
	}	
}
