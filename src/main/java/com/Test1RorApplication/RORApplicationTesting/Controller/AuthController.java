package com.Test1RorApplication.RORApplicationTesting.Controller;

import com.Test1RorApplication.RORApplicationTesting.DTO.AuthenticationResponse;
import com.Test1RorApplication.RORApplicationTesting.DTO.LoginRequest;
import com.Test1RorApplication.RORApplicationTesting.DTO.RegisterRequest;
import com.Test1RorApplication.RORApplicationTesting.Exception.InvalidCredentialsException;
import com.Test1RorApplication.RORApplicationTesting.Exception.UnauthorizedAccessException;
import com.Test1RorApplication.RORApplicationTesting.Service.AuthService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/auth")
public class AuthController {

    private final AuthService authService;

    public AuthController(AuthService authService) {
        this.authService = authService;
    }

    @PutMapping("/accountVerification/")
    public ResponseEntity<String> verifyAccount(@PathVariable String token) {
        return new ResponseEntity<>("Account activated successfully", HttpStatus.OK);
    }

    @PostMapping("/signin")
    @CrossOrigin(origins = "http://localhost:63342", allowCredentials = "true")
    public ResponseEntity<?> login(@RequestBody LoginRequest loginRequest) {
        try {
            AuthenticationResponse response = authService.login(loginRequest);
            return ResponseEntity.ok(response);
        } catch (UsernameNotFoundException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body( e.getMessage());
        } catch (InvalidCredentialsException e) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Invalid credentials: " + e.getMessage());
        } catch (UnauthorizedAccessException e) {
            return ResponseEntity.status(HttpStatus.FORBIDDEN).body("Unauthorized access: " + e.getMessage());
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("An error occurred during authentication");
        }
    }
}
