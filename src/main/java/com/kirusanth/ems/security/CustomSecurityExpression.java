package com.kirusanth.ems.security;

import com.kirusanth.ems.model.User;
import com.kirusanth.ems.repository.UserRepository;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;

@Component("customSecurityExpression")
public class CustomSecurityExpression {

    private final UserRepository userRepository;

    public CustomSecurityExpression(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    public boolean isCurrentUser(Long userId) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String currentUsername = authentication.getName();
        User user = userRepository.findById(userId).orElse(null);
        
        return user != null && user.getUsername().equals(currentUsername);
    }
}
