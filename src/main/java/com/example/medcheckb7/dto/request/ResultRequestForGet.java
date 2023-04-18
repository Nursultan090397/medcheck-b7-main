package com.example.medcheckb7.dto.request;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class ResultRequestForGet {
    private Long userId;

    private String resultOrderNumber;

    private String resultFile;
}
