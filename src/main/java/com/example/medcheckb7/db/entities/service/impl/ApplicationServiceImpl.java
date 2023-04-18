package com.example.medcheckb7.db.entities.service.impl;

import com.example.medcheckb7.converter.ApplicationRequestConverter;
import com.example.medcheckb7.converter.ApplicationResponseConverter;
import com.example.medcheckb7.db.entities.Application;
import com.example.medcheckb7.db.entities.service.ApplicationService;
import com.example.medcheckb7.db.repository.ApplicationRepository;
import com.example.medcheckb7.dto.request.ApplicationRequest;
import com.example.medcheckb7.dto.response.ApplicationResponse;
import com.example.medcheckb7.dto.response.ApplicationResponseListForAdmin;
import com.example.medcheckb7.dto.response.SimpleResponse;
import com.example.medcheckb7.exceptions.BadRequestException;
import com.example.medcheckb7.exceptions.NotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class ApplicationServiceImpl implements ApplicationService {

    private final ApplicationRepository applicationRepository;
    private final ApplicationRequestConverter applicationRequestConvert;
    private final ApplicationResponseConverter applicationResponseConvert;

    @Override
    public ApplicationResponse saveApplication(ApplicationRequest applicationRequest) {
        Application application = applicationRequestConvert.create(applicationRequest);
        for (Character i : applicationRequest.getFirstName().toCharArray()) {
            if (!Character.isAlphabetic(i)) {
                throw new BadRequestException("Numbers can not be inserted in lastname");
            }
        }
        applicationRepository.save(application);
        return applicationResponseConvert.viewApplication(application);
    }

    @Override
    public List<ApplicationResponse> getAllApplications() {
        return applicationResponseConvert.view(applicationRepository.findAll());
    }

    private List<Application> search(String name) {
        String text = name == null ? "" : name;
        return applicationRepository.searchApplication(text.toUpperCase());
    }

    @Override
    public ApplicationResponseListForAdmin searchApplications(String text) {
        ApplicationResponseListForAdmin responseListForAdmin = new ApplicationResponseListForAdmin();
        responseListForAdmin.setApplicationResponseListForAdmin(applicationResponseConvert.view(search(text)));
        return responseListForAdmin;
    }

    public SimpleResponse deleteSelectedApplications(List<Long> ids) {
        for (Long id : ids) {
            if (applicationRepository.findById(id).isEmpty()) {
                throw new NotFoundException("Application not found with ID: " + id);
            }
        }
        applicationRepository.deleteSelectedApplications(ids);
        return new SimpleResponse("Removed successfully");
    }

    @Override
    public ApplicationResponse checkApplications(Long id) {
        Application application = applicationRepository.findById(id).orElseThrow(() -> new NotFoundException("Application with this id not exists!"));
        applicationRepository.save(applicationRequestConvert.update(application));
        return applicationResponseConvert.viewApplication(application);
    }

}
