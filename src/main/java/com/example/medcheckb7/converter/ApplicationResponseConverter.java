package com.example.medcheckb7.converter;

import com.example.medcheckb7.db.entities.Application;
import com.example.medcheckb7.dto.response.ApplicationResponse;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

@Component
public class ApplicationResponseConverter {

    public ApplicationResponse viewApplication(Application application) {
        if (application == null) {
            return null;
        }
        ApplicationResponse applicationResponse = new ApplicationResponse();
        applicationResponse.setId(application.getId());
        applicationResponse.setFirstName(application.getFirstName());
        applicationResponse.setPhoneNumber(application.getPhoneNumber());
        applicationResponse.setDate(application.getDate());
        applicationResponse.setStatus(application.getStatus());
        return applicationResponse;
    }

    public List<ApplicationResponse> view(List<Application> applications) {
        List<ApplicationResponse> applicationResponses = new ArrayList<>();
        for (Application application : applications) {
            applicationResponses.add(viewApplication(application));
        }
        return applicationResponses;
    }
}
