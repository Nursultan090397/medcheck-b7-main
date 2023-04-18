package com.example.medcheckb7.db.entities.service;

import com.example.medcheckb7.dto.request.ResultRequest;
import com.example.medcheckb7.dto.response.ResultResponseForUser;
import com.example.medcheckb7.dto.response.SimpleResponse;
import com.example.medcheckb7.exceptions.NotFoundException;
import org.springframework.stereotype.Service;

import java.util.List;


@Service
public interface ResultService {
    List<ResultResponseForUser> findResultsByOrderNumber(String orderNumber) throws NotFoundException;

    SimpleResponse addResult(ResultRequest resultRequest);
}

