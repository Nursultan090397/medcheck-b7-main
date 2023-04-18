package com.example.medcheckb7.db.entities.service;
import com.example.medcheckb7.dto.request.ApplicationRequest;
import com.example.medcheckb7.dto.response.ApplicationResponse;
import com.example.medcheckb7.dto.response.ApplicationResponseListForAdmin;
import com.example.medcheckb7.dto.response.SimpleResponse;

import java.util.List;

public interface ApplicationService {

    ApplicationResponse saveApplication(ApplicationRequest applicationRequest);

    List<ApplicationResponse> getAllApplications();

    ApplicationResponseListForAdmin searchApplications(String text);

    SimpleResponse deleteSelectedApplications(List<Long> ids);

    ApplicationResponse checkApplications(Long id);
}
