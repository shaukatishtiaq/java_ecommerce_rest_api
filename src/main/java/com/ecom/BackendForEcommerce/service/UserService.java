package com.ecom.BackendForEcommerce.service;

import com.ecom.BackendForEcommerce.dto.user.UserUpdateDto;
import com.ecom.BackendForEcommerce.entity.User;

import java.util.List;

public interface UserService {
    User createUser(User userDetails);

    User getUserById(Long userId);

    List<User> getAllUsers();

    User findByEmail(String email);

    User updateUser(String userEmail, UserUpdateDto userUpdatePayload);

    boolean deleteUser(String userEmail);
}
