package com.ecom.BackendForEcommerce.controller;

import com.ecom.BackendForEcommerce.dto.user.UserResponseDto;
import com.ecom.BackendForEcommerce.dto.user.UserUpdateDto;
import com.ecom.BackendForEcommerce.entity.User;
import com.ecom.BackendForEcommerce.service.DeletionUserService;
import com.ecom.BackendForEcommerce.service.UserService;
import com.ecom.BackendForEcommerce.utility.SecurityContextUtility;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Collections;
import java.util.List;
import java.util.Map;

@RestController
@RequiredArgsConstructor
public class UserController {
    private final UserService userService;
    private final ModelMapper modelMapper;
    private final DeletionUserService deletionUserService;

    @GetMapping("/admin/users/{userId}")
    public ResponseEntity<UserResponseDto> getUserById(@PathVariable("userId") Long userId) {
        User foundUser = userService.getUserById(userId);

        UserResponseDto result = modelMapper.map(foundUser, UserResponseDto.class);

        return new ResponseEntity<>(result, HttpStatus.OK);
    }

    @GetMapping("/admin/users")
    public ResponseEntity<List<UserResponseDto>> getAllUsers() {
        List<User> users = userService.getAllUsers();

        List<UserResponseDto> result = users.stream().map(user -> modelMapper.map(user, UserResponseDto.class)).toList();

        return new ResponseEntity<>(result, HttpStatus.OK);
    }

    @PatchMapping("/users")
    public ResponseEntity<UserResponseDto> updateUserData(@Valid @RequestBody UserUpdateDto userUpdatePayload) {

        String userEmail = SecurityContextUtility.getUserEmailFromSecurityContext();

        User updatedUser = userService.updateUser(userEmail, userUpdatePayload);

        UserResponseDto result = modelMapper.map(updatedUser, UserResponseDto.class);

        return new ResponseEntity<>(result, HttpStatus.OK);
    }

    @DeleteMapping("/users")
    public ResponseEntity<Map<String, Object>> deleteUser() {
        String userEmail = SecurityContextUtility.getUserEmailFromSecurityContext();

        User user = userService.findByEmail(userEmail);

        deletionUserService.deleteUser(user);

        Boolean isUserDeleted = userService.deleteUser(userEmail);

        return new ResponseEntity<>(Collections.singletonMap("message", "User has been deleted."), HttpStatus.OK);
    }
}
