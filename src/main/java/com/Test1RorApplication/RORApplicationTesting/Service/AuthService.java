package com.Test1RorApplication.RORApplicationTesting.Service;

import com.Test1RorApplication.RORApplicationTesting.DTO.AuthenticationResponse;
import com.Test1RorApplication.RORApplicationTesting.DTO.LoginRequest;
import com.Test1RorApplication.RORApplicationTesting.DTO.RegisterRequest;
import com.Test1RorApplication.RORApplicationTesting.Model.User;
import com.Test1RorApplication.RORApplicationTesting.Repository.UserRepository;
import com.Test1RorApplication.RORApplicationTesting.Security.JWTProvider;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.Instant;

@Service
@AllArgsConstructor
public class AuthService {

    private final PasswordEncoder passwordEncoder;
    private final UserRepository userRepository;
    private final AuthenticationManager authenticationManager;
    private final JWTProvider jwtProvider;
    private final UserDetailsServiceImpl userDetailsService;

    @Transactional
    public void signup(RegisterRequest registerRequest) {
        User user = new User();
        user.setEmail(registerRequest.getEmail());
        user.setPassword(passwordEncoder.encode(registerRequest.getPassword()));
        user.setUsername(registerRequest.getUsername());
        user.setCreated(Instant.now());
        user.setActive(false);
        user.setAdmin(registerRequest.isAdmin());
        user.setPhNumber(registerRequest.getPhNumber());
        user.setWardNumber(registerRequest.getWardNumber());
    }

    @Transactional(readOnly = true)
    public User getCurrentUser() {
        String username = SecurityContextHolder.getContext().getAuthentication().getName();
        return userRepository.findByUsername(username)
                .orElseThrow(() -> new UsernameNotFoundException("User name not found - " + username));
    }

    public AuthenticationResponse login(LoginRequest loginRequest) {
        authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(loginRequest.getUsername(), loginRequest.getPassword()));
        UserDetails userDetails = userDetailsService.loadUserByUsername(loginRequest.getUsername());
        String token = jwtProvider.generateToken(userDetails);
        return new AuthenticationResponse(token, loginRequest.getUsername());

    }
}
