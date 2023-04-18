package com.example.medcheckb7.db.entities.service;

import com.example.medcheckb7.dto.request.PasswordChangeRequest;
import com.example.medcheckb7.dto.request.UserRequest;
import com.example.medcheckb7.dto.response.SimpleResponse;
import com.example.medcheckb7.dto.response.UserResponse;
import com.example.medcheckb7.dto.response.UserResponseForGetResult;
import com.example.medcheckb7.dto.response.UserResponseResultList;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public interface UserService {

    UserResponse getUserById(Long id);

    UserResponse updateUser(Long userId, UserRequest userRequest);

    SimpleResponse changeUserPassword(Long userId, PasswordChangeRequest passwordChangeRequest);

    List<UserResponseForGetResult> getAllUsers();

    SimpleResponse deleteUsers(Long id);

    UserResponseResultList getUserByIdForAdmin(Long id);

    List<UserResponseForGetResult> searchUsers(String text);

    SimpleResponse forgotPassword(String email, String link);
}