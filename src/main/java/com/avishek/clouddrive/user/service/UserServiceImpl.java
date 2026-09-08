package com.avishek.clouddrive.user.service;

import com.avishek.clouddrive.exceptions.EmailAlreadyExistsException;
import com.avishek.clouddrive.exceptions.ResourceNotFoundException;
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
        CreateUserResponse response = mapToCreateUserResponse(user);
        return response;
    }

    @Override
    public CreateUserResponse findById(Long id) {
        User user = userRepository.findById(id).
                orElseThrow(() -> new ResourceNotFoundException("User","id",id));
        CreateUserResponse response = mapToCreateUserResponse(user);
        return response;
    }


//  **************HELPER FUNCTIONS**************

    private CreateUserResponse mapToCreateUserResponse(User user){
        return new CreateUserResponse(
                user.getId(),
                user.getName(),
                user.getEmail(),
                user.getCreatedAt()
        );
    }
}
