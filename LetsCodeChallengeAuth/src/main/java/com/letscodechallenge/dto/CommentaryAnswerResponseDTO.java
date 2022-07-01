package com.letscodechallenge.dto;

import com.letscodechallenge.entity.Commentary;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.beans.BeanUtils;
import org.springframework.util.CollectionUtils;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Getter
@Setter
@NoArgsConstructor
public class CommentaryAnswerResponseDTO {

    private String description;
    private UserResponseDTO user = new UserResponseDTO();
    private List<CommentaryResponseDTO> answers = new ArrayList<>();

    public CommentaryAnswerResponseDTO(Commentary commentary) {
        this.description = commentary.getDescription();
        if (commentary.getUser() != null) {
            BeanUtils.copyProperties(commentary.getUser(), this.user);
        }
        if (CollectionUtils.isEmpty(commentary.getAnswers())) {
            this.answers = commentary.getAnswers().stream().map(CommentaryResponseDTO::new).collect(Collectors.toList());
        }

    }
}
