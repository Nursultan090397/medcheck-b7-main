package com.example.medcheckb7.converter;

import com.example.medcheckb7.db.entities.Application;
import com.example.medcheckb7.dto.request.ApplicationRequest;
import org.springframework.stereotype.Component;

import java.time.LocalDate;

@Component
public class ApplicationRequestConverter {

    public Application create(ApplicationRequest applicationRequest) {
        if (applicationRequest == null) {
            return null;
        }
        Application application = new Application();
        application.setFirstName(applicationRequest.getFirstName());
        application.setPhoneNumber(applicationRequest.getPhoneNumber());
        application.setDate(LocalDate.now());
        application.setStatus(false);
        return application;
    }

    public Application update(Application application){
        if (!application.getStatus()){
            application.setStatus(true);
        } else if (application.getStatus()){
            application.setStatus(false);
        }
        return application;
    }
}
