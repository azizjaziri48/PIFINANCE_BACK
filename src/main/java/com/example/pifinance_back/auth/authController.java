package com.example.pifinance_back.auth;

import com.example.pifinance_back.config.JwtService;
import com.example.pifinance_back.Entities.Client;
import com.example.pifinance_back.Entities.SocialUser;
import com.example.pifinance_back.Repositories.ClientRepository;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.Map;
import java.util.Optional;
@RestController
@RequestMapping("/auth")
@RequiredArgsConstructor
@CrossOrigin(origins = "*", maxAge = 3600)


public class authController {
    @Autowired
    private final authServices service;
    @Autowired

    private final ClientRepository UR;
    @Autowired
    private final JwtService js;
    @Autowired
    private final UserDetails ud ;
    @Autowired
    private final resetService s ;
    private static final Logger logger = LoggerFactory.getLogger(authController.class);

    @PostMapping("/login")
    public ResponseEntity<authResponse> login(@RequestBody authRequest loginRequest) {
        try {
            // Perform authentication
            String token = service.login(loginRequest.getEmail(), loginRequest.getPwd_user());
            Client authenticatedUser = s.getUserByEmail(loginRequest.getEmail());

            // Create the AuthResponse object and populate its fields
            authResponse response = new authResponse();
            response.setToken(token);
            response.setWelcome("Welcome, " + authenticatedUser.getFirstname());
            response.setMessage("Authentication successful");
            response.setRole(authenticatedUser.getRole());
            response.setFirstname(authenticatedUser.getFirstname());
            response.setCin(authenticatedUser.getCin());
            response.setLastname(authenticatedUser.getLastname());
            response.setId_user(Math.toIntExact(authenticatedUser.getId()));

            // Return the AuthResponse object
            return ResponseEntity.ok(response);
        } catch (AuthenticationException e) {
            // Handle authentication errors
            authResponse errorResponse = new authResponse();
            errorResponse.setErrorMessage("Invalid email or password");
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(errorResponse);
        }
    }



    @PostMapping(value = "/register")
    public ResponseEntity<ApiResponse> register(@RequestBody registerRequest registerRequest)
                                                {
        try {
            String email = registerRequest.getEmail();
            String password = registerRequest.getPassword();
            String firstname = registerRequest.getFirstname();
            String lastname = registerRequest.getLastname();
            String cin = registerRequest.getCin();
            String address = registerRequest.getAddress();
            String phonenumber = registerRequest.getPhonenumber();

            if (UR.existsByEmail(email)) {
                return new ResponseEntity<>(new ApiResponse("User with this email already exists"), HttpStatus.BAD_REQUEST);
            }

            // Call your service method to register the user with the extracted data
            service.register(email, password, firstname, lastname, cin, address, phonenumber, null);

            return ResponseEntity.ok(new ApiResponse("User registered successfully!"));

        } catch (IllegalArgumentException e) {
            return new ResponseEntity<>(new ApiResponse(e.getMessage()), HttpStatus.BAD_REQUEST);
        }
                                                }
    @DeleteMapping("/logout/{id}")
    public ResponseEntity<Map<String, String>> logout(@PathVariable Long id) {
        try {
            // Perform logout for the user with the given id
            service.logout(id);

            // Create a map for the response
            Map<String, String> response = new HashMap<>();
            response.put("status", "success");
            response.put("message", "User logged out successfully!");

            // Return a success message in the response
            return ResponseEntity.ok(response);
        } catch (Exception e) {
            // Handle logout errors
            Map<String, String> errorResponse = new HashMap<>();
            errorResponse.put("status", "error");
            errorResponse.put("message", e.getMessage());

            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(errorResponse);
        }
    }

    @PostMapping("/forgot-password")
    public ResponseEntity<String> forgotPassword(@RequestBody String email) {
        try {
            s.sendPasswordResetEmail(email);
            return ResponseEntity.ok("Password reset email sent!");
        } catch (UsernameNotFoundException e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(e.getMessage());
        }
    }

    @PostMapping("/reset-password")
    public ResponseEntity<String> resetPassword(@RequestParam String token, @RequestBody String newPassword) {
        try {
            s.resetPassword(token, newPassword);
            return ResponseEntity.ok("Password reset successful!");
        } catch (IllegalArgumentException e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(e.getMessage());
        }
    }
    @PostMapping("/google-login")
    public ResponseEntity<authResponse> googleLogin(@RequestBody SocialUser socialUser) {
        logger.info("Received Social User: {}", socialUser.toString());

        // Extract email and check if this user already exists in your DB
        Optional<Client> userOptional = UR.findByEmail(socialUser.getEmail());

        // Define a user reference outside the conditional check
        Client user;
        if (!userOptional.isPresent()) {
            user = new Client();
            user.setEmail(socialUser.getEmail());
            user.setFirstname(socialUser.getFirstName());
            user.setLastname(socialUser.getLastName());
            user.setName(socialUser.getName());
        } else {
            user = userOptional.get();
        }

        // This line updates the token for both new and existing users
        user.setToken(socialUser.getIdToken()); // Set idToken from SocialUser to token in Client

        user = UR.save(user); // Save and get the entity back which should have the ID populated

        // Generate a JWT token for this user
        String token = js.generateToken(user);

        authResponse response = new authResponse();
        response.setToken(token);
        response.setId_user(Math.toIntExact(user.getId())); // Set the id_user with the ID from the saved user

        return ResponseEntity.ok(response);
    }

    }






