package com.letscodechallenge.dto;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class CommentaryAnswerRequestDTO {

    private String description;
    private Long userId;
    private Long answeredCommentaryId;

}
