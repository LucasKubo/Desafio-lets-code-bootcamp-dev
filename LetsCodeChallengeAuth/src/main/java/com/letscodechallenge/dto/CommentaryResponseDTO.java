package com.letscodechallenge.dto;

import com.letscodechallenge.entity.Commentary;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.beans.BeanUtils;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Getter
@Setter
@NoArgsConstructor
public class CommentaryResponseDTO {

    private String description;

    private UserResponseDTO user = new UserResponseDTO();
    private List<CommentaryResponseDTO> answers = new ArrayList<>();

    public CommentaryResponseDTO(Commentary commentary) {
        this.description = commentary.getDescription();
        if(commentary.getUser() != null) {
            BeanUtils.copyProperties(commentary.getUser(),this.user);
        }
        if(commentary.getAnswers() != null){
            this.answers = commentary.getAnswers().stream().map(CommentaryResponseDTO::new).collect(Collectors.toList());
        }
    }
}
