package com.example.medcheckb7.dto.response;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;

@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor
public class ScheduleResponseForSearch {
    private Long id;

    private ExpertResponseForUser expert;

    private List<ScheduleDateAndTimeResponse> scheduleDateAndTimeResponse;
}
