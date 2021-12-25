package com.shabunya.carrent.services.jwtservices;

import com.shabunya.carrent.model.User;
import com.shabunya.carrent.services.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Component;

@Component
public class CustomUserDetailsService implements UserDetailsService {
    @Autowired
    private UserService userService;

    @Override
    public CustomUserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        try {
            User user = userService.findByLogin(username);
            return CustomUserDetails.fromUserEntityToCustomUserDetails(user);
        } catch (Exception e) {
            throw new UsernameNotFoundException("user not found");
        }
    }
}

