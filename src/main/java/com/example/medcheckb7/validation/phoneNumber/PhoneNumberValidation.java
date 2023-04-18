package com.example.medcheckb7.validation.phoneNumber;


import com.example.medcheckb7.exceptions.BadRequestException;
import org.springframework.stereotype.Component;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;
import java.util.Arrays;

@Component
public class PhoneNumberValidation implements ConstraintValidator<PhoneValid, String> {

    @Override
    public boolean isValid(String phoneNumber, ConstraintValidatorContext constraintValidatorContext) {
        if (phoneNumber.length() == 13 && phoneNumber.startsWith("+996")) {
            String mnc = phoneNumber.substring(4, 7);
            String subscriberNumber = phoneNumber.substring(7);
            if (Arrays.asList("500", "501", "502", "504", "505", "507", "508", "509", "700", "701", "702", "703", "704", "705", "706", "707", "708", "709",
                    "770", "771", "772", "773", "774", "775", "776", "777", "778", "779", "220", "221", "222", "223", "224", "225", "227",
                    "999","990", "755", "550", "551", "552", "553", "554", "555", "556", "557", "559").contains(mnc)) {
                return subscriberNumber.matches("^[0-9/-/+]{6,10}$");
            } else {
                throw new BadRequestException("The number code does not match the Kyrgyz number");
            }
        } else {
            throw new BadRequestException("the number must start with +996 or contain 13 digits");
        }
    }

}
