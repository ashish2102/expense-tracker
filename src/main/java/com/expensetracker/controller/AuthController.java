package com.expensetracker.controller;

import com.expensetracker.model.User;
import com.expensetracker.service.AuthService;
import com.expensetracker.service.JwtService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.Authentication;
//import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

import java.util.Map;
//import java.util.logging.Logger;

@RestController
@RequestMapping("/api/auth")
public class AuthController {
    //private static final Logger LOGGER = Logger.getLogger(AuthController.class.getName());

    @Autowired
    private AuthService authService;

    @Autowired
    private AuthenticationManager authenticationManager;

    @Autowired
    private JwtService jwtService;

    @PostMapping("/register")
    public ResponseEntity<?> registerUser(@RequestBody User user) {
        User savedUser = authService.registerUser(user);
        return ResponseEntity.ok(Map.of("message", "User registered successfully", "user", savedUser));
    }

    @PostMapping("/login")
    public ResponseEntity<?> authenticateUser(@RequestBody User user) {
        System.out.println("Login Invoked");
        Authentication authentication = authenticationManager.authenticate(
        new UsernamePasswordAuthenticationToken(user.getUsername(), user.getPassword())
    );
    SecurityContextHolder.getContext().setAuthentication(authentication);
    //System.out.println("Login Invoked 2");
    String token = jwtService.generateToken(user.getUsername());
    //System.out.println("Login Invoked 3");
    return ResponseEntity.ok(Map.of("token", token));
    }


}
