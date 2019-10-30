package org.saheb.moviecatalog.controller;

import java.util.List;
import java.util.stream.Collectors;

import org.saheb.moviecatalog.model.CatalogItem;
import org.saheb.moviecatalog.model.UserRating;
import org.saheb.moviecatalog.service.MovieInfo;
import org.saheb.moviecatalog.service.RatingInfo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/catalog")
public class CatalogController {
	
	@Autowired
	private MovieInfo movieInfo;
	@Autowired
	private RatingInfo ratingInfo;

	@RequestMapping("/{userId}")
	public List<CatalogItem> getCatalog(@PathVariable("userId") String userId) {
		UserRating userRating = ratingInfo.getRating(userId);
		System.out.println("After rating");
		return userRating.getRatings().stream().map(rating -> movieInfo.getMovieInfo(rating)).collect(Collectors.toList());
		/*UserRating userRating = restTemplate.getForObject("http://localhost:8083/ratingsdata/user/" + userId,UserRating.class);

		return userRating.getRatings().stream().map(rating -> {
			Movie movie = restTemplate.getForObject("http://localhost:8082/movies/" + rating.getMovieId(),Movie.class);
			return new CatalogItem(movie.getName(), movie.getDescription(), rating.getRating());
		}).collect(Collectors.toList());*/
	}
}
