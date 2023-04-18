package com.example.medcheckb7.dto.request;

import lombok.*;

@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class ExpertRequest {

    private String expertImage;

    private String expertFirstName;

    private String expertLastName;

    private Long serviceId;

    private String expertPosition;

    private String expertInformation;
}