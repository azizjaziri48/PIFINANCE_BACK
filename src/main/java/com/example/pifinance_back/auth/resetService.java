package com.example.pifinance_back.auth;

import com.example.pifinance_back.Entities.Client;
import com.example.pifinance_back.Repositories.ClientRepository;
import lombok.AllArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.Optional;
import java.util.UUID;

@AllArgsConstructor
@Service
public class resetService {

    private final RestTemplate restTemplate = new RestTemplate();
    @Autowired
    private final ClientRepository userRepository;
    @Autowired
    authServices emailService ;

    @Autowired
    private JavaMailSender mailSender;
    @Autowired
    private static final Logger logger = LoggerFactory.getLogger(resetService.class); // replace 'YourClassName' with the name of your class
    @Autowired
    private final PasswordEncoder passwordEncoder;

    public void sendPasswordResetEmail(String email) {
        String actualEmail = email
                .replace("{", "")
                .replace("}", "")
                .replace("\"email\":\"", "")
                .replace("\"", "")
                .trim();
        // Log the extracted email for debugging
        System.out.println("Extracted Email: " + actualEmail);


        Optional<Client> userOptional = userRepository.findByEmail(actualEmail);

        System.out.println("Looking for user with email: " + actualEmail); // logging
        if (!userOptional.isPresent()) {
            System.out.println("User not found!"); // logging
            throw new UsernameNotFoundException("No user found with this email");
        }

        Client user = userOptional.get();
        String token = UUID.randomUUID().toString();
        user.setToken(token);
        userRepository.save(user);

        String resetUrl = "http://localhost:8092/auth/reset-password?token=" + token;
        emailService.sendResetPasswordEmail(actualEmail, resetUrl);
    }
    public void resetPassword(String token, String rawPasswordInput) {
        String actualPassword = extractPasswordFromRawInput(rawPasswordInput);

        Optional<Client> userOptional = userRepository.findByToken(token);
        if (!userOptional.isPresent()) {
            throw new IllegalArgumentException("Invalid token");
        }

        Client user = userOptional.get();

        // Log the extracted password (for debugging purposes)
        logger.info("Extracted new password: {}", actualPassword);

        user.setPwd_user(passwordEncoder.encode(actualPassword));  // Hashing the password before saving
        userRepository.save(user);
    }


    public String extractPasswordFromRawInput(String rawInput) {
        return rawInput
                .replace("{", "")
                .replace("}", "")
                .replace("\"newPassword\":\"", "")
                .replace("\"", "")
                .trim();
    }
    public Client getUserByEmail(String email) {
        return userRepository.findByEmail1(email);
    }

}