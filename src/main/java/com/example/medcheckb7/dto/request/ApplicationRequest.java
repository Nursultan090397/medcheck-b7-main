package com.example.medcheckb7.dto.request;

import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

@Getter
@Setter
public class ApplicationRequest {

    @NotBlank(message = "Invalid firstName: Empty firstName")
    @NotNull(message = "Invalid firstName:  firstName is NULL")
    @Size(min = 3, max = 30, message = "Invalid firstName: Must be of 2 - 30 characters")
    private String firstName;

    private String phoneNumber;
}
