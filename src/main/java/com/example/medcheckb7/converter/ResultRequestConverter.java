package com.example.medcheckb7.converter;

import com.example.medcheckb7.db.entities.ClinicService;
import com.example.medcheckb7.db.entities.Result;
import com.example.medcheckb7.db.entities.User;
import com.example.medcheckb7.db.repository.ClinicServiceRepository;
import com.example.medcheckb7.db.repository.UserRepository;
import com.example.medcheckb7.dto.request.ResultRequest;
import com.example.medcheckb7.exceptions.NotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.security.SecureRandom;
import java.time.LocalTime;
import java.util.HashSet;

@RequiredArgsConstructor
@Component
public class ResultRequestConverter {
    private final UserRepository userRepository;
    private final ClinicServiceRepository clinicServiceRepository;

    private String generateUniqueCode() {
        SecureRandom random = new SecureRandom();
        String characters = "ABCDEFGHIJKLMNOPQRSTUVWXYZ0123456789";
        StringBuilder sb = new StringBuilder(19);
        HashSet<String> uniqueCodes = new HashSet<>();

        while (uniqueCodes.size() < 1) {
            for (int i = 0; i < 19; i++) {
                sb.append(characters.charAt(random.nextInt(characters.length())));
            }
            String code = sb.toString();
            if (!uniqueCodes.contains(code)) {
                uniqueCodes.add(code);
                return code;
            }
            sb.setLength(0);
        }
        return null;
    }

    public Result addResult(ResultRequest resultRequest) {
        if (resultRequest == null) {
            return null;
        }
        Result result = new Result();
        result.setResultFile(resultRequest.getFile());
        result.setDate(resultRequest.getDate());
        result.setTime(LocalTime.now());
        User user = userRepository.findById(resultRequest.getUserId()).orElseThrow(() -> new NotFoundException("User with " + resultRequest.getUserId() + "not exists"));
        ClinicService clinicService = clinicServiceRepository.findById(resultRequest.getClinicServiceId()).orElseThrow(() -> new NotFoundException("ClinicService with " + resultRequest.getClinicServiceId() + "not exists"));
        result.setUser(user);
        result.setClinicService(clinicService);
        result.setResultOrderNumber(generateUniqueCode());
        return result;
    }
}
