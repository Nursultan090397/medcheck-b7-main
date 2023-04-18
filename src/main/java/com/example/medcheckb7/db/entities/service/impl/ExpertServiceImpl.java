package com.example.medcheckb7.db.entities.service.impl;

import com.example.medcheckb7.converter.ExpertRequestConverter;
import com.example.medcheckb7.converter.ExpertResponseConverter;
import com.example.medcheckb7.db.entities.ClinicService;
import com.example.medcheckb7.db.entities.Expert;
import com.example.medcheckb7.db.entities.User;
import com.example.medcheckb7.db.entities.service.AdminAndUserGetAllExpert;
import com.example.medcheckb7.db.entities.service.ExpertService;
import com.example.medcheckb7.db.repository.ClinicServiceRepository;
import com.example.medcheckb7.db.repository.ExpertRepository;
import com.example.medcheckb7.dto.request.ExpertRequest;
import com.example.medcheckb7.dto.request.ExpertRequests;
import com.example.medcheckb7.dto.response.*;
import com.example.medcheckb7.exceptions.BadRequestException;
import com.example.medcheckb7.exceptions.NotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;


@Service
@RequiredArgsConstructor
@Transactional
@EnableGlobalMethodSecurity(prePostEnabled = true)
public class ExpertServiceImpl implements ExpertService {
    private final ClinicServiceRepository clinicServiceRepository;

    private final ExpertRepository expertRepository;
    private final ClinicServiceRepository serviceRepository;
    private final ExpertRequestConverter expertRequestConverter;
    private final ExpertResponseConverter expertResponseConverter;

    @Override
    public ExpertResponseForAddExpert addExperts(ExpertRequest expertRequest) throws BadRequestException {
        ClinicService clinicService = serviceRepository.findById(expertRequest.getServiceId()).orElseThrow(
                () -> new NotFoundException("Service with id= %d not found!"));
        Expert expert = expertRequestConverter.addExperts(expertRequest);
        for (Character i : expertRequest.getExpertFirstName().toCharArray()) {
            if (!Character.isAlphabetic(i)) {
                throw new BadRequestException("Numbers cannot be inserted in expert first names");
            }
        }

        for (Character i : expertRequest.getExpertLastName().toCharArray()) {
            if (!Character.isAlphabetic(i)) {
                throw new BadRequestException("Numbers cannot be inserted in expert last names");
            }
        }
        expert.setExpertStatus(false);
        clinicService.addExperts(expert);
        expert.setClinicService(clinicService);
        expertRepository.save(expert);
        return expertResponseConverter.viewExpertForUser(expert);
    }


    private List<Expert> searchExpert(String name, Pageable pageable) {
        String text = name == null ? "" : name;
        return expertRepository.searchExpert(text.toUpperCase(), pageable);
    }


    public List<? extends AdminAndUserGetAllExpert> getAllExpert(User authentication, String text, Integer size) {
        User user = authentication;
        if (user.getRole().getRoleName().equals("ADMIN")) {
            if (text != null) {
                ExpertResponseListForAdmin expertResponse = new ExpertResponseListForAdmin();
                expertResponse.setExpertResponseGetAllForAdminList(expertResponseConverter.viewListForAdmin(searchExpert(text, null)));
                return expertResponse.getExpertResponseGetAllForAdminList();
            }
            List<ExpertResponseForAdmin> expertResponseForAdmins = expertResponseConverter.viewListForAdmin(expertRepository.findAll());
            expertResponseForAdmins.sort(Comparator.comparing(ExpertResponseForAdmin::getExpertFirstName));
            return expertResponseForAdmins;
        } else {
            List<ExpertsGetAllResponse> expertsList = new ArrayList<>();
            Pageable pageable = PageRequest.of(0, size);
            Page<ClinicService> clinicServices = clinicServiceRepository.findAll(pageable);
            if (text != null) {
                List<Expert> experts = searchExpert(text, pageable);
                List<ExpertsResponse> expertsResponses = new ArrayList<>();
                for (Expert expert : experts) {
                    ExpertsResponse response = new ExpertsResponse(expert.getId(), expert.getExpertImage(), expert.getExpertLastName() + " " + expert.getExpertFirstName(), expert.getExpertPosition());
                    expertsResponses.add(response);
                }
                ExpertsGetAllResponse expertsGetAllResponse = new ExpertsGetAllResponse(null, null, expertsResponses);
                expertsList.add(expertsGetAllResponse);
            } else {
                for (ClinicService clinicService : clinicServices) {
                    List<Expert> experts = expertRepository.findByClinicService(clinicService);
                    List<ExpertsResponse> expertsResponses = new ArrayList<>();
                    for (Expert expert : experts) {
                        ExpertsResponse response = new ExpertsResponse(expert.getId(), expert.getExpertImage(), expert.getExpertLastName() + " " + expert.getExpertFirstName(), expert.getExpertPosition());
                        expertsResponses.add(response);
                    }
                    ExpertsGetAllResponse expertsGetAllResponse = new ExpertsGetAllResponse(clinicService.getId(), clinicService.getClinicServiceName(), expertsResponses);
                    expertsList.add(expertsGetAllResponse);
                }
            }
            return expertsList;
        }
    }

    @Override
    public ExpertResponseForAddExpert getExpertById(Long id) throws NotFoundException {
        Expert expert = expertRepository.findById(id).orElseThrow(() -> new NotFoundException("Expert with this id not found exception"));
        return expertResponseConverter.viewExpertForUser(expert);
    }

    @Transactional
    @Override
    public ExpertResponseForUser updateExpert(Long id, ExpertRequests expertRequest) throws NotFoundException {
        Expert expert = expertRepository.findById(id).orElseThrow(() -> new NotFoundException("Expert with this id not found exception"));
        Expert expert1 = expertRepository.save(expertRequestConverter.update(expertRequest, expert));
        return expertResponseConverter.viewForUser(expert1);
    }

    @Override
    public SimpleResponse deleteExpert(Long id) {
        Expert expert = expertRepository.findById(id).orElseThrow(() -> new NotFoundException("Expert with this id not found exception"));
        expertRepository.delete(expert);
        return new SimpleResponse("The expert removed!");
    }

    @Override
    public ExpertResponseForAdmin checkStatus(Long id) {
        Expert expert = expertRepository.findById(id).orElseThrow(() -> new NotFoundException("Expert with this id not exists!"));
        expertRepository.save(expertRequestConverter.updateStatus(expert));
        return expertResponseConverter.viewForAdmin(expert);
    }
}