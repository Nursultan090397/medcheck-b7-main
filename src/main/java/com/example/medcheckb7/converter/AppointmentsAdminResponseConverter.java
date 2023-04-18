package com.example.medcheckb7.converter;

import com.example.medcheckb7.db.entities.ClinicService;
import com.example.medcheckb7.db.entities.Expert;
import com.example.medcheckb7.db.entities.OnlineEntry;
import com.example.medcheckb7.dto.response.AppointmentsAdminResponse;
import com.example.medcheckb7.dto.response.ClinicServiceResponseForEntry;
import com.example.medcheckb7.dto.response.ExpertResponseForEntry;
import lombok.NoArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@NoArgsConstructor
public class AppointmentsAdminResponseConverter {

    public AppointmentsAdminResponse viewApp(OnlineEntry onlineEntry) {
        if (onlineEntry == null) {
            return null;
        }
        AppointmentsAdminResponse appointmentsAdminResponse = new AppointmentsAdminResponse();
        appointmentsAdminResponse.setId(onlineEntry.getId());
        appointmentsAdminResponse.setLocalDate(onlineEntry.getRecordedDate());
        appointmentsAdminResponse.setLocalTime(onlineEntry.getRecordedTime());
        appointmentsAdminResponse.setUserName(onlineEntry.getUserName());
        appointmentsAdminResponse.setUserEmail(onlineEntry.getUserEmail());
        appointmentsAdminResponse.setUserPhoneNumber(onlineEntry.getUserPhoneNumber());
        appointmentsAdminResponse.setServiceName(toResponse(onlineEntry.getClinicService()));
        appointmentsAdminResponse.setExpert(toResponse(onlineEntry.getExpert()));
        return appointmentsAdminResponse;
    }

    public ClinicServiceResponseForEntry toResponse(ClinicService clinicService) {
        if (clinicService == null) {
            return null;
        }
        ClinicServiceResponseForEntry clinicServiceResponseForEntry = new ClinicServiceResponseForEntry();
        clinicServiceResponseForEntry.setServiceName(clinicService.getClinicServiceName());
        return clinicServiceResponseForEntry;
    }

    public ExpertResponseForEntry toResponse(Expert expert) {
        if (expert == null) {
            return null;
        }
        ExpertResponseForEntry expertResponseForEntry = new ExpertResponseForEntry();
        expertResponseForEntry.setFullName(expert.getExpertLastName() + " " + expert.getExpertFirstName());
        return expertResponseForEntry;
    }

}
