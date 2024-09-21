package com.blog.controllers;

import com.blog.payloads.ApiResponse;
import com.blog.payloads.UserDto;
import com.blog.services.UserService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/users")

public class UserController {

@Autowired
    private UserService userService;

    @PostMapping("/")
    public ResponseEntity<UserDto> createUser(@Valid @RequestBody  UserDto userDto){

        UserDto createdUserDto=this.userService.createUser(userDto);
        return new ResponseEntity<>(createdUserDto, HttpStatus.CREATED);


    }

    @PutMapping("/{userId}")
    public ResponseEntity<UserDto> updateUser(@Valid @RequestBody UserDto userDto, @PathVariable("userId") Integer uId){

        UserDto updatedUser = this.userService.updateUser(userDto,uId);
        return ResponseEntity.ok(updatedUser);
    }

    @PreAuthorize("hasRole('ADMIN')")
    @DeleteMapping("/{userId}")
    public ResponseEntity<ApiResponse> deleteUser(@PathVariable("userId") Integer uId){

        this.userService.deleteUser(uId);
        return new ResponseEntity<ApiResponse>(new ApiResponse("user Deleted Successfully",true),HttpStatus.OK);


    }
    @GetMapping("/")
    public ResponseEntity<List<UserDto>> getAllUsers(){

        return ResponseEntity.ok(this.userService.getAllUsers());
    }
    @GetMapping("/{userId}")
    public ResponseEntity<UserDto> getUsersById(@PathVariable Integer userId){

        return ResponseEntity.ok(this.userService.getUserById(userId));
    }

}
