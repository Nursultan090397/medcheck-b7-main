package com.example.medcheckb7.db.entities.service;

import com.example.medcheckb7.db.entities.User;
import com.example.medcheckb7.dto.request.OnlineEntryRequest;
import com.example.medcheckb7.dto.response.*;

import java.time.LocalDate;
import java.util.List;

public interface OnlineEntryService {

    List<OnlineEntryResponseForUser> getOnlineEntriesByUserId(User authentication);

    OnlineEntryResponses getOnlineEntryWithUser(User authentication, Long id);

    SimpleResponse deleteAll(User authentication);

    List<ClinicServiceResponse> getAllClinicService();

    List<ExpertResponseOnlineEntry> getAllExpertByClinicId(Long clinicServiceId);

    OnlineEntryResponse saveOnlineEntry(OnlineEntryRequest onlineEntryRequest);

    SimpleResponse cancelEntry(Long onlineEntryId);

    List<AppointmentsAdminResponse> getAll(String text);

    SimpleResponse deleteAppointments(List<Long> ids);

    List<ScheduleAndTimeResponse> getTimesToEntry(Long expertId, LocalDate localDate);

}
