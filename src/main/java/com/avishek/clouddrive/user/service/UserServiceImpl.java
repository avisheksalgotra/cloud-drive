package com.avishek.clouddrive.user.service;

import com.avishek.clouddrive.exceptions.EmailAlreadyExistsException;
import com.avishek.clouddrive.exceptions.ResourceNotFoundException;
import com.avishek.clouddrive.user.dto.CreateUserRequest;
import com.avishek.clouddrive.user.dto.CreateUserResponse;
import com.avishek.clouddrive.user.dto.UpdateUserRequest;
import com.avishek.clouddrive.user.dto.UserResponse;
import com.avishek.clouddrive.user.entity.AppRole;
import com.avishek.clouddrive.user.entity.Role;
import com.avishek.clouddrive.user.entity.User;
import com.avishek.clouddrive.user.repository.RoleRepository;
import com.avishek.clouddrive.user.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Service
public class UserServiceImpl implements UserService {


    @Autowired
    private PasswordEncoder passwordEncoder;
    @Autowired
    private final UserRepository userRepository;
    private final RoleRepository roleRepository;
    @Autowired
    public UserServiceImpl(UserRepository userRepository, RoleRepository roleRepository) {
        this.userRepository = userRepository;
        this.roleRepository = roleRepository;
    }

    @Override
    public CreateUserResponse createUser(CreateUserRequest request) {
        if(userRepository.existsByEmail(request.getEmail())){
            throw new EmailAlreadyExistsException(request.getEmail());
        }

        User user = new User();
        user.setEmail(request.getEmail());
        user.setName(request.getName());
        user.setPasswordHash(
                passwordEncoder.encode(request.getPassword())
        );


        Set<Role> role = new HashSet<>();


        Role userRole = roleRepository.findByRoleName(AppRole.ROLE_USER)
                .orElseThrow(() -> new RuntimeException("Error: Role is not Found"));
        role.add(userRole);
        user.setRoles(role);
        userRepository.save(user);
        return mapToCreateUserResponse(user);
    }

    @Override
    public CreateUserResponse findById(Long id) {
        User user = userRepository.findById(id).
                orElseThrow(() -> new ResourceNotFoundException("User","id",id));
        return mapToCreateUserResponse(user);
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

    @Override
    public CreateUserResponse deleteById(Long id) {
        User user = userRepository.findById(id).
                orElseThrow(() -> new ResourceNotFoundException("User","id",id));
        userRepository.delete(user);
        return mapToCreateUserResponse(user);

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
