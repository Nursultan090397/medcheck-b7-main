package com.example.medcheckb7.dto.response;

import com.example.medcheckb7.db.entities.service.AdminAndUserGetAllExpert;
import lombok.*;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class ExpertResponseForUser implements AdminAndUserGetAllExpert {
    private Long id;

    private String expertFirstName;

    private String expertLastName;

    private String expertPosition;

    private String expertImage;
}
