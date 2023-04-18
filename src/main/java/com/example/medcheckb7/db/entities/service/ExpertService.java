package com.example.medcheckb7.db.entities.service;

import com.example.medcheckb7.db.entities.User;
import com.example.medcheckb7.dto.request.ExpertRequest;
import com.example.medcheckb7.dto.request.ExpertRequests;
import com.example.medcheckb7.dto.response.ExpertResponseForAddExpert;
import com.example.medcheckb7.dto.response.ExpertResponseForAdmin;
import com.example.medcheckb7.dto.response.ExpertResponseForUser;
import com.example.medcheckb7.dto.response.SimpleResponse;
import com.example.medcheckb7.exceptions.NotFoundException;

import java.io.IOException;
import java.util.List;

public interface ExpertService {
    ExpertResponseForAddExpert addExperts( ExpertRequest expertRequest) throws IOException;

    ExpertResponseForAddExpert getExpertById(Long id);

    ExpertResponseForUser updateExpert(Long id, ExpertRequests expertRequest) throws NotFoundException;

    SimpleResponse deleteExpert(Long id);

    List<? extends AdminAndUserGetAllExpert> getAllExpert(User authentication, String text, Integer size);

    ExpertResponseForAdmin checkStatus(Long id);
}