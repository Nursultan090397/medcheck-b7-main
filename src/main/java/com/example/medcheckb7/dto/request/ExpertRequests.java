package com.example.medcheckb7.dto.request;

import lombok.*;

@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class ExpertRequests {

    private String expertImage;

    private String expertFirstName;

    private String expertLastName;

    private String expertPosition;

    private String expertInformation;
}