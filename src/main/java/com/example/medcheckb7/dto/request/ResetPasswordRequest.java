package com.example.medcheckb7.dto.request;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor

public class ResetPasswordRequest {
    private Long userId;

    @NotNull
    @NotBlank
    private String newPassword;
}
