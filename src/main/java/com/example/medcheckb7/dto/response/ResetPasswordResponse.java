package com.example.medcheckb7.dto.response;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor
public class ResetPasswordResponse {
    private Long id;
    private String firstName;
    private String lastName;
    private String email;
    private String role;
    private String jwt;
    private String message;

}
