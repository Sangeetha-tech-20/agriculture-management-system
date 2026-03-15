package com.agriculture.controller;

import com.agriculture.dto.ApiResponse;
import com.agriculture.dto.LoginRequest;
import com.agriculture.dto.RegisterRequest;
import com.agriculture.entity.User;
import com.agriculture.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/auth")
@CrossOrigin(origins = "*")
public class AuthController {
    @Autowired
    private UserService userService;

    @PostMapping("/register")
    public ResponseEntity<ApiResponse> register(@RequestBody RegisterRequest request) {
        try {
            User user = userService.register(request);
            return ResponseEntity.ok(new ApiResponse(true, "User registered successfully"));
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(new ApiResponse(false, e.getMessage()));
        }
    }

    @PostMapping("/login")
    public ResponseEntity<?> login(@RequestBody LoginRequest request) {
        try {
            String token = userService.login(request);
            User user = userService.findByEmail(request.email());
            LoginResponse loginResponse = new LoginResponse(token, user);
            return ResponseEntity.ok(loginResponse);
        } catch (Exception e) {
            // Return proper error response instead of throwing runtime exception
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED)
                    .body(new ApiResponse(false, "Invalid email or password"));
        }
    }
}

record LoginResponse(String token, User user) {}