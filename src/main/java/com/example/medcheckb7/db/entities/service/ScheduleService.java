package com.example.medcheckb7.db.entities.service;

import com.example.medcheckb7.dto.request.ScheduleRequest;
import com.example.medcheckb7.dto.request.ScheduleUpdateRequest;
import com.example.medcheckb7.dto.response.ScheduleResponse;
import com.example.medcheckb7.dto.response.ScheduleResponseForSearch;
import com.example.medcheckb7.dto.response.SimpleResponse;
import com.example.medcheckb7.exceptions.NotFoundException;

import java.io.IOException;
import java.util.List;

public interface ScheduleService {

    ScheduleResponse addSchedule(ScheduleRequest scheduleRequest);

    List<ScheduleResponseForSearch> searchAndFetchAllSchedules(String expertFName) throws NotFoundException;

    byte[] patientExportExcel(String expertFName) throws IOException;

    SimpleResponse updateSchedule(ScheduleUpdateRequest scheduleUpdateRequest);

    SimpleResponse delete(List<Long> ids);
}



