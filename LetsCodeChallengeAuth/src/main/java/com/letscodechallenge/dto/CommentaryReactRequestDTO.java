package com.letscodechallenge.dto;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class CommentaryReactRequestDTO {
    private int like;
    private int deslike;
}
