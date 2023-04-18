package com.example.medcheckb7.dto.request;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class PasswordChangeRequest {

    private String oldPassword;
    private String newPassword;
}