package com.example.medcheckb7.dto.response;

import lombok.Getter;
import lombok.Setter;

import java.time.LocalDate;

@Getter
@Setter
public class ApplicationResponse {

    private Long id;

    private String firstName;

    private String phoneNumber;

    private LocalDate date;

    private Boolean status;
}
