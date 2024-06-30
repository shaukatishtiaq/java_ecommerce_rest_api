package com.ecom.BackendForEcommerce.service.impl;

import com.ecom.BackendForEcommerce.dto.user.UserUpdateDto;
import com.ecom.BackendForEcommerce.entity.User;
import com.ecom.BackendForEcommerce.exception.customexception.CommonCustomException;
import com.ecom.BackendForEcommerce.repository.UserRepository;
import com.ecom.BackendForEcommerce.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;
import org.springframework.http.HttpStatus;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class UserServiceImpl implements UserService {

    private final UserRepository userRepository;
    private PasswordEncoder passwordEncoder;

    @Autowired
    public void setPasswordEncoder(@Lazy PasswordEncoder passwordEncoder) {
        this.passwordEncoder = passwordEncoder;
    }

    @Override
    @Transactional
    public User createUser(User userDetails) {
        Optional<User> foundUser = userRepository.findByEmail(userDetails.getEmail());
        if (foundUser.isPresent()) {
            throw new CommonCustomException(HttpStatus.BAD_REQUEST.value(), "User already exists.");
        }
        return userRepository.save(userDetails);
    }

    @Override
    @Transactional
    public User getUserById(Long userId) {
        Optional<User> foundUserOptional = userRepository.findById(userId);

        if (foundUserOptional.isEmpty()) {
            throw new CommonCustomException(HttpStatus.NOT_FOUND.value(), "User with id = " + userId + " not found");
        }
        return foundUserOptional.get();
    }

    @Override
    @Transactional
    public List<User> getAllUsers() {
        return userRepository.findAll();
    }

    @Override
    @Transactional
    public User findByEmail(String email) {
        Optional<User> userOptional = userRepository.findByEmail(email);
        if (userOptional.isEmpty()) {
            throw new CommonCustomException(HttpStatus.NOT_FOUND.value(), "User with email = " + email + " not found.");
        }
        return userOptional.get();
    }

    @Override
    @Transactional
    public User updateUser(String userEmail, UserUpdateDto userUpdatePayload) {
        User user = findByEmail(userEmail);

        if (userUpdatePayload.getFirstName() != null) {
            user.setFirstName(userUpdatePayload.getFirstName());
        }
        if (userUpdatePayload.getLastName() != null) {
            user.setLastName(userUpdatePayload.getLastName());
        }
        if (userUpdatePayload.getPassword() != null) {
            user.setPassword(passwordEncoder.encode(userUpdatePayload.getPassword()));
        }

        return userRepository.save(user);
    }

    @Override
    @Transactional
    public boolean deleteUser(String userEmail) {
        User user = findByEmail(userEmail);

        userRepository.delete(user);

        return true;
    }
}
