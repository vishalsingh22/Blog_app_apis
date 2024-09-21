package com.blog.services;

import com.blog.payloads.UserDto;

import java.util.List;

public interface UserService {

    UserDto registerNewUser(UserDto userDto);
    UserDto createUser(UserDto userDto);
    UserDto updateUser(UserDto userDto,Integer id);
    UserDto getUserById(Integer userId);
    List<UserDto> getAllUsers();

    void deleteUser(Integer userId);
}
