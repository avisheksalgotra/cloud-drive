package com.avishek.clouddrive.user.controller;

import com.avishek.clouddrive.user.Config;
import com.avishek.clouddrive.user.dto.CreateUserRequest;
import com.avishek.clouddrive.user.dto.CreateUserResponse;
import com.avishek.clouddrive.user.dto.UpdateUserRequest;
import com.avishek.clouddrive.user.dto.UserResponse;
import com.avishek.clouddrive.user.service.UserService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/users")
public class UserController {

    private final UserService userService;

    @Autowired
    public UserController(UserService userService) {
        this.userService = userService;
    }
    @PostMapping()
    public ResponseEntity<CreateUserResponse> createUser(@Valid @RequestBody CreateUserRequest request) {
        CreateUserResponse response = userService.createUser(request);
        return new ResponseEntity<>(response,HttpStatus.CREATED);
    }

    @GetMapping("/{id}")
    public ResponseEntity<CreateUserResponse> findById(@PathVariable Long id) {
        CreateUserResponse user = userService.findById(id);
        return new ResponseEntity<>(user, HttpStatus.OK);
    }
    @GetMapping
    public ResponseEntity<UserResponse> findAll(
            @RequestParam(name = "pageNumber",defaultValue = Config.PAGE_NUMBER,required = false) Integer pageNumber,
            @RequestParam(name = "pageSize",defaultValue = Config.PAGE_SIZE,required = false) Integer pageSize,
            @RequestParam(name = "sortBy",defaultValue = Config.SORT_BY,required = false) String sortBy,
            @RequestParam(name = "sortOrder",defaultValue = Config.SORT_ORDER,required = false) String sortOrder
    ) {
        UserResponse response = userService.findAll(pageNumber,pageSize,sortBy,sortOrder);
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    @PutMapping("/update/{id}")
    public ResponseEntity<CreateUserResponse> updateUser(@PathVariable Long id,@Valid @RequestBody UpdateUserRequest request) {
        CreateUserResponse response = userService.updateUser(id,request);
        return new ResponseEntity<>(response, HttpStatus.OK);
    }
}
