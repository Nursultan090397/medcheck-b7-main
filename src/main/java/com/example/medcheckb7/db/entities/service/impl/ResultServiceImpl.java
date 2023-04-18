package com.example.medcheckb7.db.entities.service.impl;

import com.example.medcheckb7.converter.ResultRequestConverter;
import com.example.medcheckb7.converter.ResultResponseConverter;
import com.example.medcheckb7.db.entities.Result;
import com.example.medcheckb7.db.entities.service.ResultService;
import com.example.medcheckb7.db.repository.ResultRepository;
import com.example.medcheckb7.db.repository.UserRepository;
import com.example.medcheckb7.dto.request.ResultRequest;
import com.example.medcheckb7.dto.response.ResultResponseForUser;
import com.example.medcheckb7.dto.response.SimpleResponse;
import com.example.medcheckb7.exceptions.NotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
@Transactional
public class ResultServiceImpl implements ResultService {

    private final ResultRepository resultRepository;
    private final ResultResponseConverter resultResponseConverter;
    private final ResultRequestConverter resultRequestConverter;
    private final EmailSenderService emailSenderService;
    private final UserRepository userRepository;

    @Override
    public List<ResultResponseForUser> findResultsByOrderNumber(String orderNumber) throws NotFoundException {
        List<Result> results = resultRepository.findByOrderNumber(orderNumber);

        if (results.isEmpty()) {
            throw new NotFoundException("No results found for order number: " + orderNumber);
        }
        List<ResultResponseForUser> responseList = new ArrayList<>();
        for (Result result : results) {
            ResultResponseForUser response = resultResponseConverter.toResponse(result);
            responseList.add(response);
        }
        return responseList;
    }


    public SimpleResponse addResult(ResultRequest resultRequest) {
        Result result = resultRequestConverter.addResult(resultRequest);
        resultRepository.save(result);
        emailSenderService.sendEmail(userRepository.getReferenceById(resultRequest.getUserId()).getUserEmail(), "Your results code is: "+result.getResultOrderNumber());
        return new SimpleResponse("Result successfully added and result's code sent to user's email!");
    }
}

