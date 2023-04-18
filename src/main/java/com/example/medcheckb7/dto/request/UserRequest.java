package com.example.medcheckb7.dto.request;

import com.example.medcheckb7.validation.phoneNumber.PhoneValid;
import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

@Setter
@Getter
public class UserRequest {

    @NotNull(message = "User cannot be unnamed!")
    @NotBlank(message = "User name cannot be empty!")
    @Size(min = 3, message = "User name cannot be too short!")
    @Size(max = 15, message = "User name cannot be too long!")
    private String userFirstName;

    @NotNull(message = "User cannot be unnamed!")
    @NotBlank(message = "User lastname cannot be empty!")
    @Size(min = 3, message = "User lastname cannot be too short!")
    @Size(max = 15, message = "User lastname cannot be too long!")
    private String userLastName;

    @NotNull
    @NotBlank
    @Email
    private String userEmail;

    @NotNull
    @NotBlank
    @PhoneValid
    private String userPhoneNumber;
}