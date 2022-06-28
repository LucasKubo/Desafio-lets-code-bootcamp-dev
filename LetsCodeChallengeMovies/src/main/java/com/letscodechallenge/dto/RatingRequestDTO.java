package com.letscodechallenge.dto;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class RatingRequestDTO {
	private Long userId;
	private int rating;
	private String movieId;
}
