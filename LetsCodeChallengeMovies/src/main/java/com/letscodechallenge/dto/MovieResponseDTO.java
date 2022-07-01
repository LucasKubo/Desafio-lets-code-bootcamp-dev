package com.letscodechallenge.dto;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
@NoArgsConstructor
public class MovieResponseDTO {
	//Notice: the attributes are with first capital letter because that's how ombd API provide the params.
	private String Title;

	private String Year;

	private String imdbID;

	private List<CommentaryResponseDTO> comments;

	private List<RatingResponseDTO> ratings;
}
