package com.avishek.clouddrive.user.service;

import com.avishek.clouddrive.user.dto.CreateUserRequest;
import com.avishek.clouddrive.user.dto.CreateUserResponse;


public interface UserService {
    CreateUserResponse createUser(CreateUserRequest request);
    CreateUserResponse findById(Long id);
}
