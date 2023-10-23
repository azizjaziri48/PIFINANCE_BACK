package com.example.pifinance_back.auth;

import com.example.pifinance_back.config.JwtService;
import com.example.pifinance_back.Entities.Client;
import com.example.pifinance_back.Entities.UserEnum;
import com.example.pifinance_back.Repositories.ClientRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

@Service
@RequiredArgsConstructor
public class authServices {
    @Autowired
    private JavaMailSender mailSender;

    @Autowired
    private final ClientRepository UR;
    @Autowired

    private final PasswordEncoder passwordEncoder;
    @Autowired

    private final JwtService jwtService;
    @Autowired

    private final AuthenticationManager authenticationManager;


    public String login(String email, String pwd_user) {
        try {
            // Perform authentication
            Authentication authentication = authenticationManager.authenticate(
                    new UsernamePasswordAuthenticationToken(email, pwd_user)
            );

            // If authentication is successful, load the user details
            UserDetails userDetails = (UserDetails) authentication.getPrincipal();

            // Generate JWT token
            String token = jwtService.generateToken(userDetails);

            // Get the User entity and set the token
            Client user = UR.findByEmail(email).orElse(null); // Assuming UR is your UserRepository instance
            if (user != null) {
                user.setToken(token);
                UR.save(user); // Save the updated user with the token to the database
            }

            return token;
        } catch (UsernameNotFoundException | BadCredentialsException e) {
            // Handle authentication errors
            throw new AuthenticationException("Invalid email or password") {
                // Add your custom exception handling logic here
            };
        }
    }
    public void register(String email, String pwd_user, String firstname, String lastname, String cin, String address, String phonenumber, byte[] image) {
        // Check if the user already exists in the database
        if (UR.existsByEmail(email)) {
            throw new IllegalArgumentException("User with this email already exists");
        }

        // Encrypt the password before saving it
        String encryptedPassword = passwordEncoder.encode(pwd_user);

        // Create a new user entity and set the details
        Client user = new Client();
        user.setEmail(email);
        user.setPwd_user(encryptedPassword);
        user.setFirstname(firstname);
        user.setLastname(lastname);
        user.setCin(cin);
        user.setAddress(address);
        user.setRole(UserEnum.client); // Set the role as a client
        user.setImage(image); // Set the role as a client

        // Save the new user entity to the database
        UR.save(user);
    }
    public void logout(Long id) {
        Client client = UR.findById(id).orElseThrow(() -> new UsernameNotFoundException("User not found"));
        client.setToken(null); // Invalidate the token or add it to a blacklist
        UR.save(client);
    }

    public void sendResetPasswordEmail(String email, String token) {
        // This extracts the UUID from the provided token.
        Pattern pattern = Pattern.compile("[a-fA-F0-9]{8}-[a-fA-F0-9]{4}-[a-fA-F0-9]{4}-[a-fA-F0-9]{4}-[a-fA-F0-9]{12}");
        Matcher matcher = pattern.matcher(token);

        String extractedToken = "";
        if (matcher.find()) {
            extractedToken = matcher.group(0);
        }

        String resetUrl = "http://localhost:4200/reset?token=" + extractedToken;

        SimpleMailMessage message = new SimpleMailMessage();
        message.setTo(email);
        message.setSubject("Demande de réinitialisation du mot de passe");
        message.setText("Pour réinitialiser votre mot de passe, cliquez sur le lien:\n" + resetUrl +
                "\n\nVeuillez copier le jeton de sécurité, le voici:\n" + extractedToken);
        mailSender.send(message);
    }
    public void updateClient(Long clientId, String newPwd, byte[] newImage) {
        // Trouver le client en utilisant l'ID fourni
        Client client = UR.findById(clientId).orElseThrow(() -> new UsernameNotFoundException("User not found"));

        // Vérifiez si le mot de passe fourni n'est pas vide ou null
        if (newPwd == null || newPwd.trim().isEmpty()) {
            throw new IllegalArgumentException("Le mot de passe fourni est invalide");
        }

        // Mettre à jour le mot de passe du client et l'encoder
        client.setPwd_user(passwordEncoder.encode(newPwd));

        // Vérifiez si une nouvelle image est fournie
        if (newImage != null && newImage.length > 0) {
            // Mettre à jour l'image du client
            client.setImageData(newImage);
        }

        // Enregistrer les modifications dans la base de données
        UR.save(client);
    }
    public boolean hasPasswordAndImage(Long clientId) {
        return UR.findClientWithPassword(clientId).isPresent();
    }

}


