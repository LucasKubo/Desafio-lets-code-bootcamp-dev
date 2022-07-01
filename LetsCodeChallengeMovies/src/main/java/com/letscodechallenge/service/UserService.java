package com.letscodechallenge.service;

import com.letscodechallenge.entity.User;
import com.letscodechallenge.repository.IRoleRepository;
import com.letscodechallenge.repository.IUserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class UserService {

    @Autowired
    IUserRepository userRepository;
    @Autowired
    IRoleRepository roleRepository;

    public User addScore(Long userId){
        User user = userRepository.findUserById(userId);
        if(user != null) {
            user.setScore(user.getScore() + 1);
            setUserRoleId(user);
            userRepository.save(user);
        }
        return user;
    }

    private void setUserRoleId(User user) {
        if(user.getScore()>=20 && user.getRoleId()!=2L){
            user.setRoleId(2L);
            user.setRole(roleRepository.findRoleById(2L));
        }else if(user.getScore()>=100 && user.getRoleId()!=3L){
            user.setRoleId(3L);
            user.setRole(roleRepository.findRoleById(3L));
        }else if(user.getScore()>=1000 && user.getRoleId()!=4L){
            user.setRoleId(4L);
            user.setRole(roleRepository.findRoleById(4L));
        }
    }

    public User promoteUser(Long userId) {
        User user = userRepository.findUserById(userId);
        if(user != null) {
            //Setting user role to MODERATOR
            user.setRoleId(4L);
            user.setRole(roleRepository.findRoleById(4L));
            userRepository.save(user);
        }
        return user;
    }
}
