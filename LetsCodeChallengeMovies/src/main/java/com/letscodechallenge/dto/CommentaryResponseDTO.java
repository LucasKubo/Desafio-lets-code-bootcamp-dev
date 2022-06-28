package com.letscodechallenge.dto;

import com.letscodechallenge.entity.Commentary;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class CommentaryResponseDTO {
    private String description;

    public CommentaryResponseDTO(Commentary commentary) {
        this.description = commentary.getDescription();
    }
}
