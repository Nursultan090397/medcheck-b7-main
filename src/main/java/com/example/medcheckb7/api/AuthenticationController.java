package com.example.medcheckb7.api;

import com.example.medcheckb7.db.entities.service.impl.AuthenticationServiceImpl;
import com.example.medcheckb7.dto.request.AuthenticationRequest;
import com.example.medcheckb7.dto.request.RegisterRequest;
import com.example.medcheckb7.dto.request.ResetPasswordRequest;
import com.example.medcheckb7.dto.response.AuthenticationResponse;
import com.example.medcheckb7.dto.response.ResetPasswordResponse;
import com.example.medcheckb7.dto.response.SimpleResponse;
import com.example.medcheckb7.exceptions.ExceptionResponse;
import com.google.firebase.auth.FirebaseAuthException;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import javax.mail.MessagingException;
import javax.validation.Valid;

@RestController
@RequestMapping("/api/auth")
@RequiredArgsConstructor
@CrossOrigin(origins = "*", maxAge = 3600)
@Tag(name = "Auth Api")
public class AuthenticationController {

    private final AuthenticationServiceImpl service;

    @Operation(summary = "sing up", description = "Any user can register when registering, enter the correct number code, for example +996 700xxxxxx")
    @PostMapping("/register")
    public AuthenticationResponse register(@RequestBody @Valid RegisterRequest request) {
        return service.register(request);
    }

    @Operation(summary = "sing in", description = "Any user can register")
    @PostMapping("/login")
    public AuthenticationResponse authenticate(@RequestBody AuthenticationRequest request) {
        return service.authenticate(request);
    }

    @Operation(summary = "authorization with google", description = "You can register by google")
    @PostMapping("/auth/google")
    public AuthenticationResponse authResponse(String tokenFront) throws FirebaseAuthException, ExceptionResponse {
        return service.registerAndLoginWithGoogle(tokenFront);
    }

    @Operation(summary = "Forgot password", description = "If email which you input exists,we sent the link for reset password")
    @PostMapping("/forgot")
    public SimpleResponse forgotPassword(@RequestParam(name = "email") String email, @RequestParam(name = "link") String link) throws MessagingException {
        return service.forgotPassword(email, link);
    }

    @Operation(summary = "Reset password", description = "you can change your password with this method")
    @PutMapping("/reset")
    public ResetPasswordResponse reset(@RequestBody ResetPasswordRequest request) {
        return service.resetPassword(request);
    }

}