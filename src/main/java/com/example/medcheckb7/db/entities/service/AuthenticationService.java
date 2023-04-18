package com.example.medcheckb7.db.entities.service;

import com.example.medcheckb7.dto.request.AuthenticationRequest;
import com.example.medcheckb7.dto.request.RegisterRequest;
import com.example.medcheckb7.dto.request.ResetPasswordRequest;
import com.example.medcheckb7.dto.response.AuthenticationResponse;
import com.example.medcheckb7.dto.response.ResetPasswordResponse;
import com.example.medcheckb7.dto.response.SimpleResponse;
import com.example.medcheckb7.exceptions.BadRequestException;
import com.example.medcheckb7.exceptions.ExceptionResponse;
import com.google.firebase.auth.FirebaseAuthException;

import javax.mail.MessagingException;

public interface AuthenticationService {

    AuthenticationResponse register(RegisterRequest request) throws BadRequestException;

    AuthenticationResponse authenticate(AuthenticationRequest request);

    AuthenticationResponse registerAndLoginWithGoogle(String tokenFront) throws FirebaseAuthException, ExceptionResponse;

    SimpleResponse forgotPassword(String email, String link) throws MessagingException;

    ResetPasswordResponse resetPassword(ResetPasswordRequest resetPasswordRequest);
}
