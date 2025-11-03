package com.raj.user.user_service.controller;

import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.web.bind.annotation.*;

import com.raj.user.user_service.pojo.AuthRequest;
import com.raj.user.user_service.pojo.AuthResponse;
import com.raj.user.user_service.pojo.RegisterRequest;
import com.raj.user.user_service.service.JwtService;
import com.raj.user.user_service.service.UserService;
import com.raj.user.user_service.service.UserServiceImpl;

import lombok.RequiredArgsConstructor;

import java.util.Set;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/auth")
@RequiredArgsConstructor
public class AuthController {

    private final AuthenticationManager authenticationManager;

    private final JwtService jwtService;

    private final UserServiceImpl userDetailsService;

    private final UserService userService;

    // Register a new user
    @PostMapping("/register")
    public String register(@RequestBody RegisterRequest request) {
        return userService.registerUser(request);
    }

    // Login and return JWT
    @PostMapping("/login")
    public AuthResponse login(@RequestBody AuthRequest request) {
        try {
            Authentication authentication = authenticationManager.authenticate(
                    new UsernamePasswordAuthenticationToken(request.getEmail(),
                    		request.getPassword()));

            if (authentication.isAuthenticated()) {
                var userDetails = userDetailsService.loadUserByUsername(request.getEmail());

                // Extract all roles from authenticated user
                Set<String> roles = userDetails.getAuthorities()
                        .stream()
                        .map(auth -> auth.getAuthority())
                        .collect(Collectors.toSet());

                // Generate JWT with multiple roles
                String token = jwtService.generateToken(userDetails.getUsername(), roles);

                return new AuthResponse(token);
            } else {
                throw new RuntimeException("Invalid credentials");
            }
        } catch (AuthenticationException e) {
            throw new RuntimeException("Invalid username or password");
        }
    }
}
