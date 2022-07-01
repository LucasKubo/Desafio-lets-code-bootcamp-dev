package com.letscodechallenge.restcontroller;

import com.letscodechallenge.dto.UpdateDTO;
import com.letscodechallenge.dto.UserResponseDTO;
import com.letscodechallenge.security.component.UserRequest;
import com.letscodechallenge.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

@RestController
@RequestMapping("/restricted/api/v1/user")
public class UserRestController {
    @Autowired
    UserService userService;
    @Autowired
    UserRequest userRequest;

    @RequestMapping(value ="/promote/{userId}", method = RequestMethod.PUT)
    public ResponseEntity<UpdateDTO> promoteUser (@PathVariable Long userId){
        ResponseEntity<UpdateDTO> response;
        if(userRequest.getRole().equals("MODERATOR")){
            new RestTemplate().put("http://localhost:8081/api/v1/user/promote/"+userId,
                            UserResponseDTO.class);
            response = new ResponseEntity<>(new UpdateDTO(true), HttpStatus.OK);
        }else {
            response = new ResponseEntity<>(new UpdateDTO(false), HttpStatus.FORBIDDEN);
        }
        return response;
    }

}
