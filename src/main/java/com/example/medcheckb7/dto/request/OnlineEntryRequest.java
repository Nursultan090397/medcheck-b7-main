package com.example.medcheckb7.dto.request;

import com.example.medcheckb7.validation.phoneNumber.PhoneValid;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDate;
import java.time.LocalTime;

@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
public class OnlineEntryRequest {

    private Long serviceId;

    private Long expertId;

    private LocalDate date;

    private String time;

    private String userName;

    @PhoneValid
    private String userPhoneNumber;

    private String userEmail;

}
