package com.example.medcheckb7.converter;

import com.example.medcheckb7.db.entities.Result;
import com.example.medcheckb7.db.entities.User;
import com.example.medcheckb7.dto.response.ResultResponse;
import com.example.medcheckb7.dto.response.UserResponse;
import com.example.medcheckb7.dto.response.UserResponseForGetResult;
import com.example.medcheckb7.dto.response.UserResponseResultList;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;


@Component
@RequiredArgsConstructor
public class UserResponseConverter {

    public UserResponse viewApplication(User user) {
        if (user == null) {
            return null;
        }
        UserResponse userResponse = new UserResponse();
        userResponse.setId(user.getId());
        userResponse.setUserFirstName(user.getUserFirstName());
        userResponse.setUserLastName(user.getUserLastName());
        userResponse.setUserEmail(user.getUserEmail());
        userResponse.setUserPhoneNumber(user.getUserPhoneNumber());
        return userResponse;
    }

    public UserResponseForGetResult viewResult(User user) {
        if (user == null) {
            return null;
        }
        UserResponseForGetResult userResponse = new UserResponseForGetResult();
        userResponse.setId(user.getId());
        userResponse.setUserFirstName(user.getUserFirstName());
        userResponse.setUserLastName(user.getUserLastName());
        userResponse.setUserEmail(user.getUserEmail());
        userResponse.setUserPhoneNumber(user.getUserPhoneNumber());
        Optional<Result> mostRecentResult = user.getResults().stream().max(Comparator.comparing(Result::getDate));
        if (mostRecentResult.isPresent()) {
            userResponse.setDate(mostRecentResult.get().getDate());
            userResponse.setStatus(true);
        } else userResponse.setStatus(false);
        return userResponse;
    }

    public List<UserResponseForGetResult> viewResultForUser(List<User> users) {
        List<UserResponseForGetResult> expertResponses = new ArrayList<>();
        for (User user : users) {
            expertResponses.add(viewResult(user));
        }
        return expertResponses.stream()
                .filter(user -> user.getId() != 1)
                .collect(Collectors.toList());
    }

    public UserResponseResultList viewResultList(User user) {
        if (user == null) {
            return null;
        }
        if (user.getResults() == null) {
            UserResponse userResponse = new UserResponse();
            userResponse.setId(user.getId());
            userResponse.setUserFirstName(user.getUserFirstName());
            userResponse.setUserLastName(user.getUserLastName());
            userResponse.setUserEmail(user.getUserEmail());
            userResponse.setUserPhoneNumber(user.getUserPhoneNumber());
            return (UserResponseResultList) userResponse;
        }
        UserResponseResultList userResponse = new UserResponseResultList();
        userResponse.setId(user.getId());
        userResponse.setUserFirstName(user.getUserFirstName());
        userResponse.setUserLastName(user.getUserLastName());
        userResponse.setUserEmail(user.getUserEmail());
        userResponse.setUserPhoneNumber(user.getUserPhoneNumber());
        List<ResultResponse> childDTOs = user.getResults()
                .stream()
                .map(this::convertToChildDTO)
                .collect(Collectors.toList());
        userResponse.setResults(childDTOs);

        return userResponse;
    }

    public ResultResponse convertToChildDTO(Result result) {
        ResultResponse resultResponse = new ResultResponse();
        resultResponse.setCode(result.getResultOrderNumber());
        resultResponse.setFile(result.getResultFile());
        resultResponse.setDate(result.getDate());
        resultResponse.setClinicService(result.getClinicService().getClinicServiceName());
        resultResponse.setTime(result.getTime());
        return resultResponse;
    }
}

