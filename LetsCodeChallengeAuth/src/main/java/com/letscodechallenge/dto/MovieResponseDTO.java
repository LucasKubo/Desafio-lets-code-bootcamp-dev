package com.letscodechallenge.dto;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
@NoArgsConstructor
public class MovieResponseDTO {
	
	private String title;

	private String year;

	private String imdbID;

	private List<CommentaryResponseDTO> comments;

	private List<RatingResponseDTO> ratings;
}
