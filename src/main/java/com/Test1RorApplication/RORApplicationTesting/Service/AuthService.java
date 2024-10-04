package com.Test1RorApplication.RORApplicationTesting.Service;

import com.Test1RorApplication.RORApplicationTesting.DTO.AuthenticationResponse;
import com.Test1RorApplication.RORApplicationTesting.DTO.LoginRequest;
import com.Test1RorApplication.RORApplicationTesting.DTO.RegisterRequest;
import com.Test1RorApplication.RORApplicationTesting.Exception.InvalidCredentialsException;
import com.Test1RorApplication.RORApplicationTesting.Exception.UnauthorizedAccessException;
import com.Test1RorApplication.RORApplicationTesting.Model.Users;
import com.Test1RorApplication.RORApplicationTesting.Repository.UserRepository;
import com.Test1RorApplication.RORApplicationTesting.Security.JWTProvider;
import com.Test1RorApplication.RORApplicationTesting.Exception.RegistrationException;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.Instant;
import java.util.regex.Pattern;

@Service
@AllArgsConstructor
@Slf4j
public class AuthService {

    private final PasswordEncoder passwordEncoder;
    private final UserRepository userRepository;
    private final AuthenticationManager authenticationManager;
    private final JWTProvider jwtProvider;
    private final UserDetailsServiceImpl userDetailsService;

    @Transactional
    public void signup(RegisterRequest registerRequest) throws RegistrationException {
        try {
            if (!performBackgroundCheck(registerRequest)) {
                throw new RegistrationException("Background check failed");
            }

            if (userRepository.findByUsername(registerRequest.getUsername()).isPresent()) {
                throw new RegistrationException("Username already exists");
            }

            if (userRepository.findByEmail(registerRequest.getEmail()).isPresent()) {
                throw new RegistrationException("Email already registered");
            }

            Users user = new Users();
            user.setEmail(registerRequest.getEmail());
            user.setPassword(passwordEncoder.encode(registerRequest.getPassword()));
            user.setUsername(registerRequest.getUsername());
            user.setCreated(Instant.now());
            user.setActive(registerRequest.isActive());
            user.setAdmin(false);
            user.setPhNumber(registerRequest.getPhNumber());
            user.setWardNumber(registerRequest.getWardNumber());

            userRepository.save(user);
            log.info("User registered successfully: {}", user.getUsername());
        } catch (RegistrationException e) {
            log.error("Registration failed: {}", e.getMessage());
            throw e;
        } catch (Exception e) {
            log.error("Unexpected error during registration", e);
            throw new RegistrationException("Registration failed due to an unexpected error");
        }
    }

    private boolean performBackgroundCheck(RegisterRequest registerRequest) {
        // Check if username is at least 3 characters long
        if (registerRequest.getUsername().length() < 3) {
            log.warn("Username too short: {}", registerRequest.getUsername());
            return false;
        }

        // Check if email is valid
        String emailRegex = "^[a-zA-Z0-9_+&*-]+(?:\\.[a-zA-Z0-9_+&*-]+)*@(?:[a-zA-Z0-9-]+\\.)+[a-zA-Z]{2,7}$";
        if (!Pattern.compile(emailRegex).matcher(registerRequest.getEmail()).matches()) {
            log.warn("Invalid email: {}", registerRequest.getEmail());
            return false;
        }

        // Check if password is at least 8 characters long
        if (registerRequest.getPassword().length() < 8) {
            log.warn("Password too short for user: {}", registerRequest.getUsername());
            return false;
        }

        // Check if phone number is valid (assuming 10-digit number)
        if (String.valueOf(registerRequest.getPhNumber()).length() != 10) {
            log.warn("Invalid phone number for user: {}", registerRequest.getUsername());
            return false;
        }

        // Check if ward number is positive
        if (registerRequest.getWardNumber() <= 0) {
            log.warn("Invalid ward number for user: {}", registerRequest.getUsername());
            return false;
        }

        // All checks passed
        return true;
    }

    @Transactional(readOnly = true)
    public Users getCurrentUser() {
        String username = SecurityContextHolder.getContext().getAuthentication().getName();
        return userRepository.findByUsername(username)
                .orElseThrow(() -> new UsernameNotFoundException("User name not found - " + username));
    }

    public AuthenticationResponse login(LoginRequest loginRequest) {
        Users user = userRepository.findByUsername(loginRequest.getUsername())
                .orElseThrow(() -> new UsernameNotFoundException("User not found: " + loginRequest.getUsername()));

        if ((!user.isAdmin() && loginRequest.isAdmin()) || (user.isAdmin() && !loginRequest.isAdmin())) {
            throw new UnauthorizedAccessException("Unauthorized access attempt");
        }

        try {
            authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(loginRequest.getUsername(), loginRequest.getPassword()));
        } catch (BadCredentialsException e) {
            throw new InvalidCredentialsException("Invalid username or password");
        }

        UserDetails userDetails = userDetailsService.loadUserByUsername(loginRequest.getUsername());
        String token = jwtProvider.generateToken(userDetails);
        return new AuthenticationResponse(token, user.isAdmin());
    }

}