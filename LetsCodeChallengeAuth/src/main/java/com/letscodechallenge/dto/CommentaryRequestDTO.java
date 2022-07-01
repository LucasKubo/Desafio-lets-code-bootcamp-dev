package com.letscodechallenge.dto;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class CommentaryRequestDTO {
    private String description;
    private Long userId;
    private String movieTitle;
}
