package com.avishek.clouddrive.user.service;

import com.avishek.clouddrive.user.dto.CreateUserRequest;
import com.avishek.clouddrive.user.dto.CreateUserResponse;
import com.avishek.clouddrive.user.dto.UpdateUserRequest;
import com.avishek.clouddrive.user.dto.UserResponse;
import jakarta.validation.Valid;

import java.util.List;


public interface UserService {
    CreateUserResponse createUser(CreateUserRequest request);
    CreateUserResponse findById(Long id);
    UserResponse findAll(Integer pageNumber, Integer pageSize, String sortBy, String sortOrder);

    CreateUserResponse updateUser(Long id, UpdateUserRequest request);
}
