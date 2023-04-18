package com.example.medcheckb7.dto.response;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
public class ExpertAppointmentsResponse {
    private Long id;

    private String expertFirstName;

    private String expertLastName;

    private String expertPosition;

    private String expertImage;
}
