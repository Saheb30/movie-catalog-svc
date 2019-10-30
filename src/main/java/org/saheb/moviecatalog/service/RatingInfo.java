package org.saheb.moviecatalog.service;

import java.util.Arrays;

import org.saheb.moviecatalog.model.Rating;
import org.saheb.moviecatalog.model.UserRating;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import com.netflix.hystrix.contrib.javanica.annotation.HystrixCommand;

@Service
public class RatingInfo {
	@Autowired
	private RestTemplate restTemplate;
	@HystrixCommand(fallbackMethod = "getRatingFallback")
	public UserRating getRating(String userId) {
		UserRating userRating = restTemplate.getForObject("http://ratings-data-service/ratingsdata/user/" + userId,
				UserRating.class);
		return userRating;
	}
	public UserRating getRatingFallback(String userId) {
		UserRating userRating = new UserRating();
		userRating.setUserId(userId);
		userRating.setRatings(Arrays.asList(new Rating("0", 0)));
		return userRating;
	}
}
