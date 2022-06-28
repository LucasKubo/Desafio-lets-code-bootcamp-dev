package com.letscodechallenge.service;

import com.letscodechallenge.dto.CommentaryRequestDTO;
import com.letscodechallenge.entity.Commentary;
import com.letscodechallenge.repository.ICommentaryRepository;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CommentaryService {
    @Autowired
    ICommentaryRepository commentaryRepository;

    public List<Commentary> getCommentsByMovieId(String movieId){
        return commentaryRepository.findByMovieId(movieId);
    }

    public Commentary createCommentary(CommentaryRequestDTO commentaryRequestDTO){
        Commentary commentary = new Commentary();
        BeanUtils.copyProperties(commentaryRequestDTO,commentary);
        commentaryRepository.save(commentary);
        return commentary;
    }


}
