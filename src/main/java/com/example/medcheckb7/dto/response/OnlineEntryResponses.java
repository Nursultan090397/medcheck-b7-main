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
public class OnlineEntryResponses {
    private String userFirstName;
    private String userLastName;
    private String userEmail;
    private String userPhoneNumber;
    private LocalTime recordedTime;
    private LocalDate recordedDate;
    private String onlineEntryStatus;
    private ClinicServiceResponses clinicService;
    private ExpertResponses expert;
}