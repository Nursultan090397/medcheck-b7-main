package com.example.medcheckb7.dto.response;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;

@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor

public class ExpertResponseOnlineEntry {
    private Long id;

    private String expertFirstName;

    private String expertLastName;

    private String expertPosition;

    private String expertImage;

    private LocalDate date;

    private List<LocalTime> starTime;

}
