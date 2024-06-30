package com.ecom.BackendForEcommerce.security.service;

import com.ecom.BackendForEcommerce.entity.User;
import com.ecom.BackendForEcommerce.security.model.UserPrincipal;
import com.ecom.BackendForEcommerce.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class CustomUserDetailsService implements UserDetailsService {
    private final UserService userService;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        User savedUser = userService.findByEmail(username);

        return new UserPrincipal(savedUser);
    }
}
