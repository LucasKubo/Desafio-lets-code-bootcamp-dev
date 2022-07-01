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
public class CommentaryResponseDTO {

    private  Long id;
    private String description;
    private UserResponseDTO user = new UserResponseDTO();
    private List<CommentaryResponseDTO> answers = new ArrayList<>();
    private List<CommentaryResponseDTO> mentions = new ArrayList<>();
    private int like;
    private int deslike;

    private boolean repeated;

    public CommentaryResponseDTO(Commentary commentary) {
        this.id = commentary.getId();
        this.description = commentary.getDescription();
        if(commentary.getUser() != null) {
            BeanUtils.copyProperties(commentary.getUser(),this.user);
        }
        if(!CollectionUtils.isEmpty(commentary.getAnswers())){
            this.answers = commentary.getAnswers().stream().map(CommentaryResponseDTO::new).collect(Collectors.toList());
        }
        if(!CollectionUtils.isEmpty(commentary.getMentions())){
            this.mentions = commentary.getMentions().stream().map(CommentaryResponseDTO::new).collect(Collectors.toList());
        }
        this.like = commentary.getLike();
        this.deslike = commentary.getDeslike();
        this.repeated = commentary.isRepeated();
    }
}
