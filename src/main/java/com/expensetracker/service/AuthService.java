package com.expensetracker.service;

import com.expensetracker.model.User;
import com.expensetracker.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class AuthService {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    /**
     * Register a new user with an encoded password.
     * @param user User object containing username and password.
     * @return The saved user object.
     */
    public User registerUser(User user) {
        user.setPassword(passwordEncoder.encode(user.getPassword())); // Hash the password before saving
        return userRepository.save(user);
    }

    /**
     * Authenticate user by checking the password.
     * @param username The username of the user.
     * @param rawPassword The plain text password entered by the user.
     * @return True if authentication is successful, otherwise false.
     */
    public boolean authenticateUser(String username, String rawPassword) {
        Optional<User> userOptional = userRepository.findByUsername(username);
        return userOptional.isPresent() && passwordEncoder.matches(rawPassword, userOptional.get().getPassword());
    }

    /**
     * Find user by username.
     * @param username The username to search for.
     * @return An Optional containing the user if found.
     */
    public Optional<User> findByUsername(String username) {
        return userRepository.findByUsername(username);
    }
}
