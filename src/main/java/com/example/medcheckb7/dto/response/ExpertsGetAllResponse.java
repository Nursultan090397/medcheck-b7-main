package com.example.medcheckb7.dto.response;

import com.example.medcheckb7.db.entities.service.AdminAndUserGetAllExpert;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;
@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
public class ExpertsGetAllResponse implements AdminAndUserGetAllExpert {
    private Long id;
    private String serviceName;
    private List<ExpertsResponse> expertsResponses;
}
