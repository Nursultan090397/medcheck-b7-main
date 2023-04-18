package com.example.medcheckb7.dto.response;

import com.example.medcheckb7.db.entities.enums.RepeatType;
import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.Map;

@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor
public class ScheduleResponse {
    private Long clinicId;

    private Long expertId;

    private LocalDate startDate;

    private LocalDate finishDate;


    @JsonFormat(pattern = "HH:mm:ss")
    private LocalTime startTime;

    private Boolean timeStatus;

    @JsonFormat(pattern = "HH:mm:ss")
    private LocalTime finishTime;

    private Long clockInterval;

    private RepeatType repeatType;

    private Map<String, Boolean> recurrenceDays;
}
