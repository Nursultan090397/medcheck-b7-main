package com.example.medcheckb7.db.entities.service.impl;

import com.example.medcheckb7.converter.UserResponseConverter;
import com.example.medcheckb7.db.entities.User;
import com.example.medcheckb7.db.entities.service.UserService;
import com.example.medcheckb7.db.repository.UserRepository;
import com.example.medcheckb7.dto.request.PasswordChangeRequest;
import com.example.medcheckb7.dto.request.UserRequest;
import com.example.medcheckb7.dto.response.SimpleResponse;
import com.example.medcheckb7.dto.response.UserResponse;
import com.example.medcheckb7.dto.response.UserResponseForGetResult;
import com.example.medcheckb7.dto.response.UserResponseResultList;
import com.example.medcheckb7.exceptions.BadCredentialsException;
import com.example.medcheckb7.exceptions.BadRequestException;
import com.example.medcheckb7.exceptions.NotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.bcrypt.BCrypt;
import org.springframework.stereotype.Service;

import java.util.List;


@Service
@RequiredArgsConstructor
public class UserServiceImpl implements UserService {

    private final UserRepository userRepository;

    private final UserResponseConverter userResponseConverter;

    private final EmailSenderService emailSenderService;

    @Override
    public UserResponse getUserById(Long id) {
        User user = userRepository.findById(id).orElseThrow(() -> new NotFoundException(String.format("User with id = %d not found ", id)));
        return userResponseConverter.viewApplication(user);
    }

    private User getAuthenticateUser() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String login = authentication.getName();
        return userRepository.findByEmail(login).orElseThrow(() -> new NotFoundException("User not found"));
    }

    @Override
    public UserResponse updateUser(Long userId, UserRequest userRequest) {
        User user = getAuthenticateUser();
        String email = user.getUserEmail();

        for (User u : userRepository.findAll()) {
            if (u.getUserEmail().equals(userRequest.getUserEmail()) && !userRequest.getUserEmail().equals(user.getUserEmail())) {
                throw new BadRequestException(String.format("This %s already exists!", userRequest.getUserEmail()));
            }
        }
        user.setUserFirstName(userRequest.getUserFirstName());
        user.setUserLastName(userRequest.getUserLastName());
        user.setUserEmail(userRequest.getUserEmail());
        user.setUserPhoneNumber(userRequest.getUserPhoneNumber());

        userRepository.save(user);

        if (!email.equals(userRequest.getUserEmail())) {
            return new UserResponse(user.getId(), user.getUserFirstName(), user.getUserLastName(), user.getUserEmail(), user.getUserPhoneNumber());
        } else {
            throw new BadCredentialsException("We couldn't changed!");
        }
    }

    @Override
    public SimpleResponse changeUserPassword(Long userId, PasswordChangeRequest passwordChangeRequest) {
        User user = userRepository.findById(userId).orElseThrow(() -> new NotFoundException(String.format("User with id = %d not found ", userId)));
        if (!BCrypt.checkpw(passwordChangeRequest.getOldPassword(), user.getPassword())) {
            throw new BadRequestException("The password is entered incorrectly!");
        }
        user.setUserPassword(BCrypt.hashpw(passwordChangeRequest.getNewPassword(), BCrypt.gensalt()));
        userRepository.save(user);
        return new SimpleResponse("Password changed successfully!");
    }

    @Override
    public List<UserResponseForGetResult> getAllUsers() {
        return userResponseConverter.viewResultForUser(userRepository.findAll());
    }

    @Override
    public SimpleResponse deleteUsers(Long id) {
        if (id == 1) {
            throw new BadRequestException("Cannot delete id \"1\" because this id belongs to the Admin");
        }
        User user = userRepository.findById(id).orElseThrow(() -> new NotFoundException("User with this id not found exception"));
        userRepository.delete(user);
        return new SimpleResponse("The user removed!");
    }

    @Override
    public UserResponseResultList getUserByIdForAdmin(Long id) {
        if (id == 1) {
            throw new BadRequestException("User with this id don't exists");
        }
        User user = userRepository.findById(id).orElseThrow(() -> new NotFoundException(String.format("User with id = %d not found ", id)));

        return userResponseConverter.viewResultList(user);
    }

    public List<UserResponseForGetResult> searchUsers(String text) {
        return userResponseConverter.viewResultForUser(userRepository.searchUser(text));
    }

    @Override
    public SimpleResponse forgotPassword(String email, String link) {
        User user = userRepository.findByEmail(email).orElseThrow(()-> new NotFoundException(String.format("user with email %s not found", email)));
        emailSenderService.sendEmail(email,"To get a new password reset link visit: " + link + "/" + user.getId());
        return new SimpleResponse("message successfully send");
    }
}
