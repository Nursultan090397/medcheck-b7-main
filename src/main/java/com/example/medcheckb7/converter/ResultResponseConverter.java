package com.example.medcheckb7.converter;

import com.example.medcheckb7.db.entities.Result;
import com.example.medcheckb7.dto.response.ResultResponseForUser;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@RequiredArgsConstructor
@Component
public class ResultResponseConverter {

    public ResultResponseForUser toResponse(Result result) {
        if (result == null) {
            return null;
        }
        ResultResponseForUser response = new ResultResponseForUser();
        response.setId(result.getId());
        response.setResultFile(result.getResultFile());
        return response;
    }
}
