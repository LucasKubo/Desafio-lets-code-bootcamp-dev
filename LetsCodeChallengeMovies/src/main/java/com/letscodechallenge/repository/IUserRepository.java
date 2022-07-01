package com.letscodechallenge.repository;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import com.letscodechallenge.entity.User;

@Repository
public interface IUserRepository extends CrudRepository<User, Long>{

	User findByUsername(String username);

	User findUserById(Long id);
	
}
