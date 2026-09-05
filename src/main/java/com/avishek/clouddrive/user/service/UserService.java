package com.avishek.clouddrive.user.service;

import com.avishek.clouddrive.user.dto.CreateUserRequest;
import com.avishek.clouddrive.user.dto.CreateUserResponse;
import org.springframework.stereotype.Service;


public interface UserService {
    CreateUserResponse createUser(CreateUserRequest request);
}
