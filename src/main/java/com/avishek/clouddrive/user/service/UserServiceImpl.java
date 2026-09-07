package com.avishek.clouddrive.user.service;

import com.avishek.clouddrive.exceptions.EmailAlreadyExistsException;
import com.avishek.clouddrive.user.dto.CreateUserRequest;
import com.avishek.clouddrive.user.dto.CreateUserResponse;
import com.avishek.clouddrive.user.entity.User;
import com.avishek.clouddrive.user.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class UserServiceImpl implements UserService {


    private final UserRepository userRepository;
    @Autowired
    public UserServiceImpl(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @Override
    public CreateUserResponse createUser(CreateUserRequest request) {
        if(userRepository.existsByEmail(request.getEmail())){
            throw new EmailAlreadyExistsException(request.getEmail());
        }
        User user = new User();
        user.setEmail(request.getEmail());
        user.setName(request.getName());
        user.setPasswordHash(request.getPassword());
        userRepository.save(user);
        CreateUserResponse response = new CreateUserResponse();
        response.setId(user.getId());
        response.setEmail(user.getEmail());
        response.setName(user.getName());
        response.setCreatedAt(user.getCreatedAt());
        return response;
    }
}
