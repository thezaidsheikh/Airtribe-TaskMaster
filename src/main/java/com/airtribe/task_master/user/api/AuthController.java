package com.airtribe.task_master.user.api;

import com.airtribe.task_master.common.response.ApiResponse;
import com.airtribe.task_master.common.response.ApiSuccess;
import com.airtribe.task_master.user.application.AuthService;
import com.airtribe.task_master.user.dto.AuthResponseDto;
import com.airtribe.task_master.user.dto.UserLoginDto;
import com.airtribe.task_master.user.dto.UserRegistrationDto;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/auth")
public class AuthController {
    private final AuthService authService;

    @Autowired
    public AuthController(AuthService authService) {
        this.authService = authService;
    }

    @ApiSuccess(status = HttpStatus.CREATED, message = "User created successfully")
    @PostMapping("/register")
    public AuthResponseDto register(@Valid @RequestBody UserRegistrationDto request) {
        return authService.register(request);
    }

    @ApiSuccess(status = HttpStatus.OK, message = "")
    @PostMapping("/login")
    public AuthResponseDto login(@Valid @RequestBody UserLoginDto request) {
        return authService.login(request);
    }
}
