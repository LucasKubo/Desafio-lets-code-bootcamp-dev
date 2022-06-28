package com.letscodechallenge.dto;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class MovieResponseDTO {

	//Notice: the attributes are with first capital letter because that's how ombd API provide the params.
		private String Title;
		private String Year;
		private String imdbID;
}
