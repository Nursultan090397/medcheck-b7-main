package com.example.medcheckb7.dto.request;

import com.example.medcheckb7.validation.password.PasswordValid;
import com.example.medcheckb7.validation.phoneNumber.PhoneValid;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.validation.Valid;
import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Valid
public class RegisterRequest {

    @NotNull(message = "First name should be not null")
    @Size(min = 2, max = 25)
    private String firstname;

    @Size(min = 2, max = 25)
    @NotNull(message = "Last name should be not null")
    private String lastname;

    @Email(message = "Email should be valid")
    @NotNull(message = "Email should be not null")
    @NotBlank(message = "Email should not be empty")
    private String email;

    @PhoneValid
    private String phoneNumber;

    @PasswordValid
    private String password;
}