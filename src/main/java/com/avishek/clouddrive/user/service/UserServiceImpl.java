package com.avishek.clouddrive.user.service;

import com.avishek.clouddrive.exceptions.EmailAlreadyExistsException;
import com.avishek.clouddrive.exceptions.ResourceNotFoundException;
import com.avishek.clouddrive.user.dto.CreateUserRequest;
import com.avishek.clouddrive.user.dto.CreateUserResponse;
import com.avishek.clouddrive.user.dto.UpdateUserRequest;
import com.avishek.clouddrive.user.dto.UserResponse;
import com.avishek.clouddrive.user.entity.User;
import com.avishek.clouddrive.user.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

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

    @Override
    public UserResponse findAll(Integer pageNumber, Integer pageSize, String sortBy, String sortOrder) {
        Sort sortByOrderAndId = sortOrder.equalsIgnoreCase("asc") ? Sort.by(sortBy).ascending()
                : Sort.by(sortBy).descending();

        Pageable pageable = PageRequest.of(pageNumber, pageSize, sortByOrderAndId);

        Page<User> usersPage = userRepository.findAll(pageable);

        System.out.println(usersPage);

        List<User> userList = usersPage.getContent();

        List<CreateUserResponse> responses = new ArrayList<>();
        userList.forEach( user -> responses.add(mapToCreateUserResponse(user)));

            UserResponse userResponse = new UserResponse();

            userResponse.setContent(responses);
            userResponse.setPageNumber(usersPage.getNumber());
            userResponse.setPageSize(usersPage.getSize());
            userResponse.setTotalPages(usersPage.getTotalPages());
            userResponse.setTotalElements(usersPage.getTotalElements());
            userResponse.setLastPage(usersPage.isLast());
            return userResponse;
    }

    @Override
    public CreateUserResponse updateUser(Long id, UpdateUserRequest request) {

        User user = userRepository.findById(id)
                .orElseThrow(() ->
                        new ResourceNotFoundException("User", "id", id)
                );

        if (request.getName() != null) {
            user.setName(request.getName());
        }

        if (request.getEmail() != null &&
                !request.getEmail().equals(user.getEmail())) {

            if (userRepository.existsByEmail(request.getEmail())) {
                throw new EmailAlreadyExistsException(request.getEmail());
            }

            user.setEmail(request.getEmail());
        }

        User updatedUser = userRepository.save(user);

        return mapToCreateUserResponse(updatedUser);
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
