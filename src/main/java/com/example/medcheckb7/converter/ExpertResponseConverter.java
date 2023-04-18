package com.example.medcheckb7.converter;

import com.example.medcheckb7.db.entities.ClinicService;
import com.example.medcheckb7.db.entities.Expert;
import com.example.medcheckb7.db.entities.service.impl.ScheduleServiceImpl;
import com.example.medcheckb7.dto.response.*;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

import static com.example.medcheckb7.converter.ScheduleResponseConverter.getExpertResponsesForUser;

@RequiredArgsConstructor
@Component
public class ExpertResponseConverter {
    private final ScheduleServiceImpl scheduleService;

    public ExpertResponseForAddExpert viewExpertForUser(Expert expert) {
        if (expert == null) {
            return null;
        }
        ExpertResponseForAddExpert expertResponseForUser = new ExpertResponseForAddExpert();
        expertResponseForUser.setId(expert.getId());
        expertResponseForUser.setExpertFirstName(expert.getExpertFirstName());
        expertResponseForUser.setExpertLastName(expert.getExpertLastName());
        expertResponseForUser.setExpertPosition(expert.getExpertPosition());
        expertResponseForUser.setExpertInformation(expert.getExpertInformation());
        expertResponseForUser.setExpertImage(expert.getExpertImage());
        expertResponseForUser.setServiceName(expert.getClinicService().getClinicServiceName());
        return expertResponseForUser;
    }

    public ExpertResponseForUser viewForUser(Expert expert) {
        if (expert == null) {
            return null;
        }
        return getExpertResponsesForUser(expert);
    }

    public ExpertResponseForAdmin viewForAdmin(Expert expert) {
        if (expert == null) {
            return null;
        }
        ExpertResponseForAdmin expertResponseGetAllForAdmin = new ExpertResponseForAdmin();
        expertResponseGetAllForAdmin.setId(expert.getId());
        expertResponseGetAllForAdmin.setExpertFirstName(expert.getExpertFirstName());
        expertResponseGetAllForAdmin.setExpertLastName(expert.getExpertLastName());
        expertResponseGetAllForAdmin.setExpertPosition(expert.getExpertPosition());
        expertResponseGetAllForAdmin.setExpertImage(expert.getExpertImage());
        expertResponseGetAllForAdmin.setService(expert.getClinicService().getClinicServiceName().substring(0, 1).toUpperCase() + expert.getClinicService().getClinicServiceName().substring(1).toLowerCase());
        expertResponseGetAllForAdmin.setExpertStatus(expert.getExpertStatus());
        if (expert.getExpertTimeTable()==null){
            expertResponseGetAllForAdmin.setExpertTimeTable(null);
        } else {
            expertResponseGetAllForAdmin.setExpertTimeTable("до " + expert.getExpertTimeTable());
        }
        return expertResponseGetAllForAdmin;
    }

    public List<ExpertResponseForUser> viewListForUser(List<Expert> experts) {
        List<ExpertResponseForUser> expertResponses = new ArrayList<>();
        for (Expert expert : experts) {
            expertResponses.add(viewForUser(expert));
        }
        return expertResponses;
    }

    public List<ExpertResponseForAdmin> viewListForAdmin(List<Expert> experts) {
        List<ExpertResponseForAdmin> expertResponses = new ArrayList<>();
        for (Expert expert : experts) {
            expertResponses.add(viewForAdmin(expert));
        }
        return expertResponses;
    }
}