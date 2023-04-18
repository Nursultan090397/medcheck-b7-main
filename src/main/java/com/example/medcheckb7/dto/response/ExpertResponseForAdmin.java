package com.example.medcheckb7.dto.response;

import com.example.medcheckb7.db.entities.service.AdminAndUserGetAllExpert;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;


@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class ExpertResponseForAdmin implements AdminAndUserGetAllExpert {
    private Long id;

    private String expertFirstName;

    private String expertLastName;

    private String expertPosition;

    private String expertImage;

    private Boolean expertStatus;

    private String expertTimeTable;

    private String service;
}
