package com.airtribe.task_master.user.application;

import com.airtribe.task_master.user.domain.User;
import com.airtribe.task_master.user.dto.AuthResponseDto;
import com.airtribe.task_master.user.dto.UserDetailDto;
import com.airtribe.task_master.user.dto.UserLoginDto;
import com.airtribe.task_master.user.dto.UserRegistrationDto;
import com.airtribe.task_master.user.infrastructure.UserRepository;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.Optional;

@Service
public class AuthService {
    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final TokenProvider tokenProvider;

    public AuthService(UserRepository userRepository, PasswordEncoder passwordEncoder, TokenProvider tokenProvider) {
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
        this.tokenProvider = tokenProvider;
    }

    public AuthResponseDto register(UserRegistrationDto user) throws RuntimeException {
        Optional<User> userDetail = userRepository.findByEmail(user.getEmail());
        if(userDetail.isPresent()) throw new RuntimeException("User with this email already exist");

        String encodedPassword = passwordEncoder.encode(user.getPassword());
        User userObj = User.builder().firstName(user.getFirstName()).lastName(user.getLastName()).email(user.getEmail()).password(encodedPassword).build();
        User userInfo = userRepository.save(userObj);

        String accessToken = tokenProvider.generateAccessToken(userInfo.getEmail(), userInfo);
        String refreshToken = tokenProvider.generateRefreshToken(user.getEmail());
        LocalDateTime expiresAt = LocalDateTime.now().plusSeconds(tokenProvider.getAccessTokenExpiry() / 1000);

        UserDetailDto userDetails = UserDetailDto.builder().firstName(userInfo.getFirstName()).lastName(userInfo.getLastName()).email(userInfo.getEmail()).phoneNum(userInfo.getPhoneNum()).userId(userInfo.getUserId()).build();
        return AuthResponseDto.builder().user(userDetails).accessToken(accessToken).refreshToken(refreshToken).expiresAt(expiresAt).build();
    }

    public AuthResponseDto login(UserLoginDto user) throws RuntimeException, UsernameNotFoundException {
        User userInfo = userRepository.<User>findByEmail(user.getEmail()).orElseThrow(() -> new UsernameNotFoundException("User not found"));
        if (!passwordEncoder.matches(user.getPassword(), userInfo.getPassword())) {
            throw new RuntimeException("Email or password is incorrect");
        }

        String accessToken = tokenProvider.generateAccessToken(userInfo.getEmail(), userInfo);
        String refreshToken = tokenProvider.generateRefreshToken(user.getEmail());
        LocalDateTime expiresAt = LocalDateTime.now().plusSeconds(tokenProvider.getAccessTokenExpiry() / 1000);

        UserDetailDto userDetails = UserDetailDto.builder().firstName(userInfo.getFirstName()).lastName(userInfo.getLastName()).email(userInfo.getEmail()).phoneNum(userInfo.getPhoneNum()).userId(userInfo.getUserId()).build();
        return AuthResponseDto.builder().user(userDetails).accessToken(accessToken).refreshToken(refreshToken).expiresAt(expiresAt).build();
    }
}
