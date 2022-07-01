package com.letscodechallenge.dto;

import com.letscodechallenge.entity.Commentary;
import com.letscodechallenge.entity.Rating;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.beans.BeanUtils;

@Getter
@Setter
@NoArgsConstructor
public class RatingResponseDTO {

    private int value;

    private UserResponseDTO user = new UserResponseDTO();

    public RatingResponseDTO(Rating rating) {
        this.value = rating.getValue();
        if(rating.getUser() != null){
            BeanUtils.copyProperties(rating.getUser(),this.user);
        }
    }
}
