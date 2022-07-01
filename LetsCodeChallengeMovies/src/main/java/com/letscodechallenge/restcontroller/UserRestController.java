package com.letscodechallenge.restcontroller;

import com.letscodechallenge.dto.UserResponseDTO;
import com.letscodechallenge.entity.User;
import com.letscodechallenge.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v1/user")
public class UserRestController {
    @Autowired
    UserService userService;

    @RequestMapping(value ="/promote/{userId}", method = RequestMethod.PUT)
    public ResponseEntity<UserResponseDTO> promoteUser (@PathVariable Long userId){
        User userPromoted = userService.promoteUser(userId);
        return ResponseEntity.ok(new UserResponseDTO(userPromoted));
    }

}
