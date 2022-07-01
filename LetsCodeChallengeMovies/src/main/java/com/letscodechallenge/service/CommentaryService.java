package com.letscodechallenge.service;

import com.letscodechallenge.dto.*;
import com.letscodechallenge.entity.Commentary;
import com.letscodechallenge.repository.ICommentaryRepository;
import com.letscodechallenge.repository.IUserRepository;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CommentaryService {
    @Autowired
    ICommentaryRepository commentaryRepository;
    @Autowired
    IUserRepository userRepository;

    @Autowired
    MovieService movieService;

    public List<Commentary> getCommentsByMovieId(String movieId){
        return commentaryRepository.findByMovieId(movieId);
    }

    public Commentary createCommentary(CommentaryRequestDTO commentaryRequestDTO){
        String movieTitle = commentaryRequestDTO.getMovieTitle();
        movieService.insertMovieIfNotExistInDataBase(movieTitle);
        String movieId = movieService.findMovieByTitleApi(movieTitle).getImdbID();
        Commentary commentary = new Commentary();
        BeanUtils.copyProperties(commentaryRequestDTO,commentary);
        commentary.setMovieId(movieId);
        commentary.setUser(userRepository.findUserById(commentary.getUserId()));
        commentaryRepository.save(commentary);
        return commentary;
    }

    public Commentary answerCommentary(CommentaryAnswerRequestDTO commentaryAnsweredRequestDTO){
        //check if commentary exists
        Commentary commentary = commentaryRepository.findCommentaryById(commentaryAnsweredRequestDTO.getAnsweredCommentaryId());
        if(commentary != null){
            Commentary commentaryAnswer = new Commentary();
            commentaryAnswer.setAnsweredCommentaryId(commentaryAnsweredRequestDTO.getAnsweredCommentaryId());
            commentaryAnswer.setDescription(commentaryAnsweredRequestDTO.getDescription());
            commentaryAnswer.setUserId(commentaryAnsweredRequestDTO.getUserId());
            commentaryAnswer.setUser(userRepository.findUserById(commentaryAnsweredRequestDTO.getUserId()));
            commentaryRepository.save(commentaryAnswer);
            return commentaryAnswer;
        }
        return null;
    }

    public Commentary metioncommentary (CommentaryMentionRequestDTO commentaryMentionRequestDTO){
        //check if commentary exists
        Commentary commentary = commentaryRepository.findCommentaryById(commentaryMentionRequestDTO.getMentionedCommentaryId());
        if(commentary != null){
            Commentary commentaryMention = new Commentary();
            commentaryMention.setMentionedCommentaryId(commentaryMentionRequestDTO.getMentionedCommentaryId());
            commentaryMention.setDescription(commentaryMentionRequestDTO.getDescription());
            commentaryMention.setUserId(commentaryMentionRequestDTO.getUserId());
            commentaryMention.setUser(userRepository.findUserById(commentaryMentionRequestDTO.getUserId()));
            commentaryRepository.save(commentaryMention);
            return commentaryMention;
        }
        return null;
    }

    public Commentary reactCommentary(Long commentaryId, CommentaryReactRequestDTO commentaryReactRequestDTO){
        Commentary commentary = commentaryRepository.findCommentaryById(commentaryId);
        if(commentary != null){
            commentary.setLike(commentary.getLike()+ commentaryReactRequestDTO.getLike());
            commentary.setDeslike(commentary.getDeslike()+ commentaryReactRequestDTO.getDeslike());
            commentaryRepository.save(commentary);
        }
        return commentary;
    }

    public Commentary markCommentaryAsRepeated(Long commentaryId, CommentaryRepeatedRequestDTO commentaryRepeatedRequestDTO) {
        Commentary commentary = commentaryRepository.findCommentaryById(commentaryId);
        if(commentary != null){
            commentary.setRepeated(commentaryRepeatedRequestDTO.isRepeated());
            commentaryRepository.save(commentary);
        }
        return commentary;
    }

    public void deleteCommentary(Long commentaryId) {
        Commentary commentary = commentaryRepository.findCommentaryById(commentaryId);
        if(commentary != null){
            commentaryRepository.deleteById(commentaryId);
        }
    }
}
