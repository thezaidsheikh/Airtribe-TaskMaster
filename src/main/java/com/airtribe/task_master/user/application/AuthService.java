package com.airtribe.task_master.user.application;

import com.airtribe.task_master.user.domain.User;
import com.airtribe.task_master.user.dto.UserLoginDto;
import com.airtribe.task_master.user.dto.UserRegistrationDto;
import com.airtribe.task_master.user.infrastructure.UserRepository;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class AuthService {
    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;

    public AuthService(UserRepository userRepository, PasswordEncoder passwordEncoder) {
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
    }

    public User register(UserRegistrationDto user) throws Exception {
        String encodedPassword = passwordEncoder.encode(user.getPassword());
        User userInfo = User.builder().firstName(user.getFirstName()).lastName(user.getLastName()).email(user.getEmail()).password(encodedPassword).build();
        return userRepository.save(userInfo);
    }

    public User login(UserLoginDto user) throws RuntimeException {
        Optional<User> userInfo = userRepository.findByEmail(user.getEmail());
        if (userInfo.isEmpty()) {
            throw new RuntimeException("User not found");
        }
        if (!passwordEncoder.matches(user.getPassword(), userInfo.get().getPassword())) {
            throw new RuntimeException("Email or password is incorrect");
        }
        return userInfo.get();
    }
}
