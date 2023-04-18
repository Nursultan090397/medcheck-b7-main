package com.example.medcheckb7.dto.response;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalTime;

@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
public class OnlineEntryResponse {
    private String massage;
    private String localDate;
    private LocalTime localTimeStart;
    private LocalTime localTimeFinish;
    private ExpertAppointmentsResponse expertAppointmentsResponse;
}