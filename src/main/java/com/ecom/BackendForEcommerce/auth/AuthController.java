package com.ecom.BackendForEcommerce.auth;

import com.ecom.BackendForEcommerce.dto.user.LoginDto;
import com.ecom.BackendForEcommerce.dto.user.UserCreateDto;
import com.ecom.BackendForEcommerce.entity.User;
import com.ecom.BackendForEcommerce.enums.UserRoleEnum;
import com.ecom.BackendForEcommerce.exception.customexception.CommonCustomException;
import com.ecom.BackendForEcommerce.service.UserService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashMap;
import java.util.Map;

@RestController
@RequestMapping("/auth")
@RequiredArgsConstructor
public class AuthController {
    private final ModelMapper modelMapper;
    private final UserService userService;
    private final PasswordEncoder passwordEncoder;
    private final JwtAuthService jwtAuthService;
    private final AuthenticationManager authenticationManager;


    @PostMapping("/register")
    public ResponseEntity<Map<String, Object>> createUser(@Valid @RequestBody UserCreateDto userPayload) {

        HashMap<String, Object> result = new HashMap<>();

        User userDetails = modelMapper.map(userPayload, User.class);

        userDetails.setUserRoleEnum(UserRoleEnum.KHARIDAAR);
        userDetails.setPassword(passwordEncoder.encode(userDetails.getPassword()));

        User savedUser = userService.createUser(userDetails);

        result.put("statusCode", HttpStatus.CREATED);
        result.put("message", "User is created");

        return new ResponseEntity<>(result, HttpStatus.CREATED);
    }

    @PostMapping("/login")
    public ResponseEntity<Map<String, Object>> login(@RequestBody LoginDto loginCredentials) {
        HashMap<String, Object> result = new HashMap<>();

        UsernamePasswordAuthenticationToken authenticationToken = new UsernamePasswordAuthenticationToken(loginCredentials.getEmail(), loginCredentials.getPassword());

        Authentication authentication = authenticationManager.authenticate(authenticationToken);

        if (authentication.isAuthenticated()) {
            result.put("token", jwtAuthService.generateToken(loginCredentials.getEmail()));

        } else {
            throw new CommonCustomException(HttpStatus.BAD_REQUEST.value(), "User login failed due to incorrect credentials");
        }

        return new ResponseEntity<>(result, HttpStatus.OK);
    }

}
