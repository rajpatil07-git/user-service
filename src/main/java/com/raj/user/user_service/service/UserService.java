package com.raj.user.user_service.service;

import java.util.List;

import com.raj.user.user_service.pojo.RegisterRequest;
import com.raj.user.user_service.pojo.UserDto;

public interface UserService {
    String registerUser(RegisterRequest request);
    List<UserDto> getAllUsers();
    UserDto getUserById(Long id);
    void deleteUser(Long id);
}