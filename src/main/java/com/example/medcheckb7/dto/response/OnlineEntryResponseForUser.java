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
public class OnlineEntryResponseForUser {
    private Long id;
    private LocalTime recordedTime;
    private LocalDate recordedDate;
    private String onlineEntryStatus;
    private ExpertResponseForUser expert;
}