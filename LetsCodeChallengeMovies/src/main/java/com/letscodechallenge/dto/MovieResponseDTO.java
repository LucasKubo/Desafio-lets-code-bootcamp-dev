package com.letscodechallenge.dto;

import com.letscodechallenge.entity.Commentary;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
public class MovieResponseDTO {

	//Notice: the attributes are with first capital letter because that's how ombd API provide the params.
	private String Title;
	private String Year;
	private String imdbID;
	private List<Commentary> comments;
}
