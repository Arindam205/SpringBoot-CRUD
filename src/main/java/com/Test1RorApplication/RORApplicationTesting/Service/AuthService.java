package com.Test1RorApplication.RORApplicationTesting.Service;

import com.Test1RorApplication.RORApplicationTesting.DTO.AuthenticationResponse;
import com.Test1RorApplication.RORApplicationTesting.DTO.LoginRequest;
import com.Test1RorApplication.RORApplicationTesting.DTO.RegisterRequest;
import com.Test1RorApplication.RORApplicationTesting.Model.Users;
import com.Test1RorApplication.RORApplicationTesting.Repository.UserRepository;
import com.Test1RorApplication.RORApplicationTesting.Security.JWTProvider;
import lombok.AllArgsConstructor;
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
        Users user = new Users();
        user.setEmail(registerRequest.getEmail());
        user.setPassword(passwordEncoder.encode(registerRequest.getPassword()));
        user.setUsername(registerRequest.getUsername());
        user.setCreated(Instant.now());
        user.setActive(true);
        user.setAdmin(false);
        user.setPhNumber(registerRequest.getPhNumber());
        user.setWardNumber(registerRequest.getWardNumber());

        userRepository.save(user);
    }

    @Transactional(readOnly = true)
    public Users getCurrentUser() {
        String username = SecurityContextHolder.getContext().getAuthentication().getName();
        return userRepository.findByUsername(username)
                .orElseThrow(() -> new UsernameNotFoundException("User name not found - " + username));
    }

    public AuthenticationResponse login(LoginRequest loginRequest) {
        Users user = userRepository.findByUsername(loginRequest.getUsername())
                .orElseThrow(() -> new UsernameNotFoundException("User name not found - " + loginRequest.getUsername()));
        if((!user.isAdmin() && loginRequest.isAdmin()) || (user.isAdmin() && !loginRequest.isAdmin())) {
            return new AuthenticationResponse("-1", false);
        }
        authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(loginRequest.getUsername(), loginRequest.getPassword()));
        UserDetails userDetails = userDetailsService.loadUserByUsername(loginRequest.getUsername());
        String token = jwtProvider.generateToken(userDetails);
        return new AuthenticationResponse(token, user.isAdmin());
    }

}
