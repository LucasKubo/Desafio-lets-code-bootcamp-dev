package com.letscodechallenge.repository;

import com.letscodechallenge.entity.Role;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface IRoleRepository extends CrudRepository<Role, Long> {

    public Role findRoleById(Long id);
}
