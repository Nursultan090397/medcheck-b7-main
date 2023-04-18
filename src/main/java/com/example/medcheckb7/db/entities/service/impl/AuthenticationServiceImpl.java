package com.example.medcheckb7.db.entities.service.impl;

import com.example.medcheckb7.config.jwt.JwtService;
import com.example.medcheckb7.db.entities.Role;
import com.example.medcheckb7.db.entities.User;
import com.example.medcheckb7.db.entities.service.AuthenticationService;
import com.example.medcheckb7.db.repository.RoleRepository;
import com.example.medcheckb7.db.repository.UserRepository;
import com.example.medcheckb7.dto.request.AuthenticationRequest;
import com.example.medcheckb7.dto.request.RegisterRequest;
import com.example.medcheckb7.dto.request.ResetPasswordRequest;
import com.example.medcheckb7.dto.response.AuthenticationResponse;
import com.example.medcheckb7.dto.response.ResetPasswordResponse;
import com.example.medcheckb7.dto.response.SimpleResponse;
import com.example.medcheckb7.exceptions.BadRequestException;
import com.example.medcheckb7.exceptions.ExceptionResponse;
import com.example.medcheckb7.exceptions.NotFoundException;
import com.google.auth.oauth2.GoogleCredentials;
import com.google.firebase.FirebaseApp;
import com.google.firebase.FirebaseOptions;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseAuthException;
import com.google.firebase.auth.FirebaseToken;
import lombok.RequiredArgsConstructor;
import org.springframework.core.io.ClassPathResource;
import org.springframework.http.HttpStatus;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import javax.annotation.PostConstruct;
import javax.mail.MessagingException;
import java.io.IOException;

@Service
@RequiredArgsConstructor
public class AuthenticationServiceImpl implements AuthenticationService {
    private final UserRepository repository;
    private final RoleRepository roleRepository;
    private final PasswordEncoder passwordEncoder;
    private final JwtService jwtService;
    private final AuthenticationManager authenticationManager;
    private final UserRepository userRepository;
    private final EmailSenderService emailSenderService;

    @PostConstruct
    void init() throws IOException {
        GoogleCredentials googleCredentials = GoogleCredentials.fromStream(new ClassPathResource("med-check.json").getInputStream());

        FirebaseOptions firebaseOptions = FirebaseOptions.builder().setCredentials(googleCredentials).build();

        FirebaseApp firebaseApp = FirebaseApp.initializeApp(firebaseOptions);
    }

    public AuthenticationResponse register(RegisterRequest request) throws BadRequestException {
        var existingUser = repository.findByEmail(request.getEmail());

        if (existingUser.isPresent()) {
            throw new BadRequestException("User with email " + request.getEmail() + " already exists");
        }

        String passwordPattern = "^(?=.*[0-9])(?=.*[a-zA-Z])[a-zA-Z0-9]+$";
        if (!request.getPassword().matches(passwordPattern)) {
            throw new BadRequestException("Password must contain at least digits and only Latin letters");
        }


        String[] emailParts = request.getEmail().split("@");
        String emailUsername = emailParts[0];
        if (request.getPassword().equals(emailUsername)) {
            throw new BadRequestException("Password should not match email username");
        }

        var user = User.builder()
                .userFirstName(request.getFirstname())
                .userLastName(request.getLastname())
                .userEmail(request.getEmail())
                .userPhoneNumber(request.getPhoneNumber())
                .userPassword(passwordEncoder.encode(request.getPassword()))
                .role(roleRepository.getById(2L))
                .build();
        repository.save(user);
        var jwtToken = jwtService.generateToken(user);
        return AuthenticationResponse.builder().token(jwtToken).roleName(user.getRole().getRoleName()).email(user.getUserEmail()).build();
    }

    public AuthenticationResponse authenticate(AuthenticationRequest request) {
        authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(request.getEmail(), request.getPassword()));
        var user = repository.findByEmail(request.getEmail()).orElseThrow();
        String jwtToken = jwtService.generateToken(user);
        return AuthenticationResponse.builder().token(jwtToken).roleName(user.getRole().getRoleName()).email(user.getUserEmail()).build();
    }

    public AuthenticationResponse registerAndLoginWithGoogle(String tokenFront) throws FirebaseAuthException, ExceptionResponse {
        FirebaseToken firebaseToken;
        try {
            firebaseToken = FirebaseAuth.getInstance().verifyIdToken(tokenFront);
        } catch (FirebaseAuthException firebaseAuthException) {
            throw new ExceptionResponse(HttpStatus.INTERNAL_SERVER_ERROR, firebaseAuthException.getClass().getSimpleName(), firebaseAuthException.getMessage());
        }
        User user;
        Role role = roleRepository.findById(2L).get();
        if (repository.findByEmail(firebaseToken.getEmail()).isEmpty()) {

            user = new User();
            role.addUser(user);
            user.setRole(role);
            user.setUserPassword(passwordEncoder.encode(firebaseToken.getEmail()));
            user.setUserFirstName(firebaseToken.getName());
            user.setUserEmail(firebaseToken.getEmail());

            roleRepository.save(role);
        }

        user = repository.findByEmail(firebaseToken.getEmail()).orElseThrow(() -> new NotFoundException(String.format("User %s not found!", firebaseToken.getEmail())));
        String token = jwtService.generateToken(user);
        return new AuthenticationResponse(user.getUserEmail(), role.getRoleName(), token);
    }

    @Override
    public SimpleResponse forgotPassword(String email, String link) throws MessagingException {
        User user = userRepository.findByEmail(email).orElseThrow(() -> new NotFoundException("user with this " + email + " not found!"));
        emailSenderService.sendEmail(email, "Your link for reset Password: " + link + "/" + user.getId());
        return new SimpleResponse("Email sent!");
    }

    @Override
    public ResetPasswordResponse resetPassword(ResetPasswordRequest request) {
        User user = userRepository.findById(request.getUserId()).orElseThrow(() -> new NotFoundException("user with " + request.getUserId() + " not found!"));
        String oldPassword = user.getPassword();
        String newPassword = passwordEncoder.encode(request.getNewPassword());
        if (!oldPassword.equals(newPassword)) {
            user.setUserPassword(newPassword);
            userRepository.save(user);
        }
        String jwt = jwtService.generateToken(user);
        return new ResetPasswordResponse(user.getId(), user.getUserFirstName(), user.getUserLastName(), user.getUserEmail(), user.getRole().getRoleName(), jwt, "Password successfully changed!");
    }
}

