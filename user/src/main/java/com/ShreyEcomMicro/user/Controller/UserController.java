package com.ShreyEcomMicro.user.Controller;



import com.ShreyEcomMicro.user.DTO.UserRequestDto;
import com.ShreyEcomMicro.user.DTO.UserResponse;
import com.ShreyEcomMicro.user.Service.UserService;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/users")
public class UserController
{

    private final UserService userService;

    private static Logger logger = LoggerFactory.getLogger(UserController.class);

    @GetMapping("/get")
    public ResponseEntity<List<UserResponse>> getaAllUser()
    {
        return new ResponseEntity<>(userService.getUserList(), HttpStatus.OK);
    }

    @PostMapping("/create")
    public ResponseEntity<UserResponse> CreateUser(@RequestBody UserRequestDto user)
    {
        return new ResponseEntity<>(userService.setUser(user), HttpStatus.CREATED);
    }

    @GetMapping("/get/{id}")
    public ResponseEntity<UserResponse> getUserById(@PathVariable Long id)
    {
        logger.info("Request received to fetch user "+id);
        UserResponse user = userService.getUserById(id);
        if(user == null){
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
        return new ResponseEntity<>(user,HttpStatus.OK);
    }

    @PutMapping("/updateuser/{id}")
    public ResponseEntity<String> updateUser(@RequestBody UserRequestDto user,@PathVariable Long id)
    {
        if(!userService.updateUser(user, id)){
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
        return new ResponseEntity<>("Updated Successfully",HttpStatus.OK);
    }
}