package com.example.medcheckb7.dto.response;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDate;
import java.time.LocalTime;

@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
public class AppointmentsAdminResponse {
    private Long id;
    private String userName;

    private String userEmail;

    private String userPhoneNumber;
    private ExpertResponseForEntry expert;
    private ClinicServiceResponseForEntry serviceName;
    private LocalDate localDate;
    private LocalTime localTime;
}
