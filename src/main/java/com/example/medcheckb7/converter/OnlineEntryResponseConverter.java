package com.example.medcheckb7.converter;

import com.example.medcheckb7.db.entities.ClinicService;
import com.example.medcheckb7.db.entities.Expert;
import com.example.medcheckb7.db.entities.OnlineEntry;
import com.example.medcheckb7.dto.response.*;
import org.jetbrains.annotations.NotNull;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

@Component
public class OnlineEntryResponseConverter {

    @NotNull
    static ExpertResponseForUser getExpertResponseForUser(Expert expert) {
        ExpertResponseForUser response = new ExpertResponseForUser();
        response.setId(expert.getId());
        response.setExpertFirstName(expert.getExpertFirstName());
        response.setExpertLastName(expert.getExpertLastName());
        response.setExpertPosition(expert.getExpertPosition());
        response.setExpertImage(expert.getExpertImage());
        return response;
    }

    static ExpertResponses getExpertResponse(Expert expert) {
        ExpertResponses response = new ExpertResponses();
        response.setExpertFirstName(expert.getExpertFirstName());
        response.setExpertLastName(expert.getExpertLastName());
        return response;
    }

    public OnlineEntryResponseForUser toResponse(OnlineEntry onlineEntry) {
        OnlineEntryResponseForUser response = new OnlineEntryResponseForUser();
        response.setId(onlineEntry.getId());
        response.setRecordedTime(onlineEntry.getRecordedTime());
        response.setRecordedDate(onlineEntry.getRecordedDate());
        response.setOnlineEntryStatus(onlineEntry.getOnlineEntryStatus());
        response.setExpert(getExpertResponseForUser(onlineEntry.getExpert()));
        return response;
    }

    public OnlineEntryResponses toResponses(OnlineEntry onlineEntry) {
        OnlineEntryResponses response = new OnlineEntryResponses();
        String[] fullName = onlineEntry.getUserName().split(" ");
        response.setUserFirstName(fullName[0]);
        response.setUserLastName(fullName[1]);
        response.setUserEmail(onlineEntry.getUserEmail());
        response.setUserPhoneNumber(onlineEntry.getUserPhoneNumber());
        response.setRecordedTime(onlineEntry.getRecordedTime());
        response.setRecordedDate(onlineEntry.getRecordedDate());
        response.setOnlineEntryStatus(onlineEntry.getOnlineEntryStatus());
        response.setClinicService(toResponses(onlineEntry.getClinicService()));
        response.setExpert(getExpertResponse(onlineEntry.getExpert()));
        return response;
    }

    public ClinicServiceResponses toResponses(ClinicService clinicService) {
        ClinicServiceResponses response = new ClinicServiceResponses();
        response.setClinicServiceName(clinicService.getClinicServiceName());
        return response;
    }
}
