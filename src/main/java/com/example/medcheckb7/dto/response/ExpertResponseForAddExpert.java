package com.example.medcheckb7.dto.response;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class ExpertResponseForAddExpert {

    private Long id;

    private String expertFirstName;

    private String expertLastName;

    private String expertPosition;

    private String expertImage;

    private String expertInformation;

    private String serviceName;
}