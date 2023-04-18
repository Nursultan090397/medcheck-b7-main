package com.example.medcheckb7.dto.request;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDate;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class ResultRequest {
    private Long clinicServiceId;
    private Long userId;
    private LocalDate date;
    private String file;
}
