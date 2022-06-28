package com.letscodechallenge.repository;

import com.letscodechallenge.entity.Movie;
import com.letscodechallenge.entity.Rating;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface IRatingRepository extends CrudRepository<Rating, Long> {

}
