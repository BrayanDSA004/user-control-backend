package com.example.user_control.controller;

import com.example.user_control.security.JwtService;
import lombok.Data;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/auth")
public class AuthController {

    private final AuthenticationManager authenticationManager;
    private final JwtService jwtService;

    @PostMapping("/login")
    public AuthResponse login(@RequestBody AuthRequest request){
        Authentication authentication = authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(
                        request.getUsername(),
                        request.getPassword()
                )
        );

        String token = jwtService.generateToken(
                (org.springframework.security.core.userdetails.UserDetails)
                        authentication.getPrincipal()
        );

        return new AuthResponse(token);
    }

    @Data
    static class AuthRequest {
        private String username;
        private String password;
    }

    @Data
    static class AuthResponse {
        private final String token;
    }
}
