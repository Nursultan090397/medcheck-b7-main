package com.example.medcheckb7.dto.request;

import com.example.medcheckb7.db.entities.enums.RepeatType;
import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.validation.constraints.NotNull;
import java.time.LocalDate;
import java.util.Map;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class ScheduleRequest {
    @NotNull(message = "The clinic Service id should be not null!")
    private Long clinicId;

    @NotNull(message = "The Expert id should be not null!")
    private Long expertId;

    @NotNull(message = "The startDate should be not null!")
    private LocalDate startDate;

    @NotNull(message = "The finishDate should be not null!")
    private LocalDate finishDate;

    @NotNull(message = "The startTime should be not null!")
    @JsonFormat(pattern = "HH:mm:ss")
    private String startTime;

    @NotNull(message = "The finishTime should be not null!")
    @JsonFormat(pattern = "HH:mm:ss")
    private String finishTime;

    @NotNull(message = "The clockInterval should be not null!")
    private Long clockInterval;

    @NotNull(message = "The repeatType should be not null!")
    private RepeatType repeatType;

    @NotNull(message = "The recurrenceDays should be not null!")
    private Map<String, Boolean> recurrenceDays;
}
